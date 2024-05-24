package com.salespoint.api.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.OrderAddItemDto;
import com.salespoint.api.dtos.OrderDto;
import com.salespoint.api.dtos.OrderPaidStatusDto;
import com.salespoint.api.dtos.OrderRemoveItemDto;
import com.salespoint.api.entities.CustomerEntity;
import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.entities.OrderEntity;
import com.salespoint.api.entities.OrderItemEntity;
import com.salespoint.api.entities.UserEntity;
import com.salespoint.api.exceptions.customs.customer.CustomerNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemNotFoundException;
import com.salespoint.api.exceptions.customs.order.ItemCantBeAddedAfterOrderPaidException;
import com.salespoint.api.exceptions.customs.order.ItemCantBeRemovedAfterOrderPaidException;
import com.salespoint.api.exceptions.customs.order.OrderCantBeDeletedAfterPaidException;
import com.salespoint.api.exceptions.customs.order.OrderNotFoundException;
import com.salespoint.api.exceptions.customs.order.OrderedItemDoesNotExistsException;
import com.salespoint.api.exceptions.customs.stock.ItemOutOfStockException;
import com.salespoint.api.exceptions.customs.user.UserNotFoundException;
import com.salespoint.api.repositories.CustomerRepository;
import com.salespoint.api.repositories.ItemRepository;
import com.salespoint.api.repositories.OrderItemRepository;
import com.salespoint.api.repositories.OrderRepository;
import com.salespoint.api.repositories.UserRepository;
import com.salespoint.api.services.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Iterable<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException());
    }

    @Override
    @Transactional
    public OrderEntity createOrder(OrderDto orderDto) {

        CustomerEntity customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException());

        UserEntity user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new UserNotFoundException());

        OrderEntity order = OrderEntity
                .builder()
                .customer(customer)
                .createdBy(user)
                .totalPrice(new BigDecimal(0))
                .orderPaid(false)
                .build();

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderEntity addItemToOrder(OrderAddItemDto orderAddItemDto) {
        OrderEntity order = orderRepository.findById(orderAddItemDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException());

        if (order.getOrderPaid().booleanValue()) {
            throw new ItemCantBeAddedAfterOrderPaidException();
        }

        ItemEntity item = itemRepository.findById(orderAddItemDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException());

        boolean doesStockAvailable = (item.getQty() - orderAddItemDto.getQty()) > 0;

        if (!doesStockAvailable) {
            throw new ItemOutOfStockException();
        }

        OrderItemEntity orderedItem = OrderItemEntity
                .builder()
                .order(order)
                .item(item)
                .qty(orderAddItemDto.getQty())
                .build();

        item.setQty(item.getQty() - orderAddItemDto.getQty());

        BigDecimal newTotalPrice = new BigDecimal(
                order.getTotalPrice().longValue() + item.getUnitPrice().longValue() * orderAddItemDto.getQty());

        order.setTotalPrice(newTotalPrice);

        orderItemRepository.save(orderedItem);

        itemRepository.save(item);

        return orderRepository.save(order);

    }

    @Override
    public OrderEntity removeItemFromOrder(OrderRemoveItemDto orderRemoveDto) {

        OrderEntity order = orderRepository.findById(orderRemoveDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException());

        if (order.getOrderPaid().booleanValue()) {
            throw new ItemCantBeRemovedAfterOrderPaidException();
        }

        ItemEntity item = itemRepository.findById(orderRemoveDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException());

        OrderItemEntity orderedItem = orderItemRepository.findByOrderAndItem(order, item)
                .orElseThrow(() -> new OrderedItemDoesNotExistsException());

        // add the ordered quantity back to the item stock
        item.setQty(item.getQty() + orderedItem.getQty());

        BigDecimal reducedTotalPrice = new BigDecimal(
                order.getTotalPrice().longValue() - (item.getUnitPrice().longValue() * orderedItem.getQty()));

        order.setTotalPrice(reducedTotalPrice);

        orderItemRepository.delete(orderedItem);

        itemRepository.save(item);

        return orderRepository.save(order);

    }

    @Override
    public OrderEntity updateOrderPaidStatus(OrderPaidStatusDto orderPaidStatusDto) {
        OrderEntity order = orderRepository.findById(orderPaidStatusDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException());

        order.setOrderPaid(orderPaidStatusDto.getPaidStatus());

        return orderRepository.save(order);

    }

    @Override
    public void deleteOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException());

        if (order.getOrderPaid().booleanValue()) {
            throw new OrderCantBeDeletedAfterPaidException();
        }

        List<OrderItemEntity> orderedItems = orderItemRepository.findAllByOrder(order)
                .orElseThrow(() -> new OrderedItemDoesNotExistsException());

        // delete all the ordered items
        for (OrderItemEntity orderedItem : orderedItems) {
            orderItemRepository.delete(orderedItem);
        }

        orderRepository.delete(order);

    }

}
