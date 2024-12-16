package com.salespoint.api.controllers;

import com.salespoint.api.utils.response.OrderItemResponse;
import com.salespoint.api.utils.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salespoint.api.dtos.OrderAddItemDto;
import com.salespoint.api.dtos.OrderDto;
import com.salespoint.api.dtos.OrderPaidStatusDto;
import com.salespoint.api.dtos.OrderRemoveItemDto;
import com.salespoint.api.entities.OrderEntity;
import com.salespoint.api.services.OrderService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        Iterable<OrderEntity> allOrders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = new ArrayList<>();
        allOrders.forEach(order -> {
            orderResponses.add(generateOrderResponse(order));
        });
        return ResponseEntity.status(HttpStatus.OK).body(orderResponses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderEntity order = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(generateOrderResponse(order));

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderDto orderDto) {
        OrderEntity order = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(generateOrderResponse(order));

    }

    @PostMapping("/add-item")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<OrderResponse> addItemToOrder(@RequestBody OrderAddItemDto orderAddItemDto) {
        OrderEntity order = orderService.addItemToOrder(orderAddItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(generateOrderResponse(order));

    }

    @PostMapping("/remove-item")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<OrderResponse> removeItemFromOrder(@RequestBody OrderRemoveItemDto orderRemoveItemDto) {
        OrderEntity order = orderService.removeItemFromOrder(orderRemoveItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(generateOrderResponse(order));

    }

    @PutMapping("/pay")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<OrderResponse> updatePaidStatus(@RequestBody OrderPaidStatusDto orderPaidStatusDto) {
        OrderEntity order = orderService.updateOrderPaidStatus(orderPaidStatusDto);
        return ResponseEntity.status(HttpStatus.OK).body(generateOrderResponse(order));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {

        orderService.deleteOrder(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order Deleted");

    }

    private OrderResponse generateOrderResponse(OrderEntity order) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(orderItem -> {
                OrderItemResponse orderItemResponse = OrderItemResponse
                        .builder()
                        .id(orderItem.getId())
                        .qty(orderItem.getQty())
                        .itemId(orderItem.getItem().getId())
                        .build();
                orderItemResponses.add(orderItemResponse);
            });
        }

        return OrderResponse
                .builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderPaid(order.getOrderPaid())
                .customerId(order.getCustomer().getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .createdBy(order.getCreatedBy().getId())
                .orderItems(orderItemResponses)
                .build();
    }
}

