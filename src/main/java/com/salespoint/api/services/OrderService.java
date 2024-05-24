package com.salespoint.api.services;

import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.OrderAddItemDto;
import com.salespoint.api.dtos.OrderDto;
import com.salespoint.api.dtos.OrderPaidStatusDto;
import com.salespoint.api.dtos.OrderRemoveItemDto;
import com.salespoint.api.entities.OrderEntity;

@Service
public interface OrderService {

    public Iterable<OrderEntity> getAllOrders();

    public OrderEntity getOrderById(Long id);

    public OrderEntity createOrder(OrderDto orderDto);

    public OrderEntity addItemToOrder(OrderAddItemDto orderAddItemDto);

    public OrderEntity removeItemFromOrder(OrderRemoveItemDto orderRemoveDto);

    public OrderEntity updateOrderPaidStatus(OrderPaidStatusDto orderPaidStatusDto);

    public void deleteOrder(Long id);
}
