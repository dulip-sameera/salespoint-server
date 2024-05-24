package com.salespoint.api.controllers;

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

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<Iterable<OrderEntity>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {

        OrderEntity order = orderService.getOrderById(id);

        return ResponseEntity.status(HttpStatus.OK).body(order);

    }

    @PostMapping
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderDto orderDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));

    }

    @PostMapping("/add-item")
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<OrderEntity> addItemToOrder(@RequestBody OrderAddItemDto orderAddItemDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addItemToOrder(orderAddItemDto));

    }

    @PostMapping("/remove-item")
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<OrderEntity> removeItemFromOrder(@RequestBody OrderRemoveItemDto orderRemoveItemDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.removeItemFromOrder(orderRemoveItemDto));

    }

    @PutMapping("/pay")
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',
    // 'CASHIER')")
    public ResponseEntity<Object> updatePaidStatus(@RequestBody OrderPaidStatusDto orderPaidStatusDto) {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderPaidStatus(orderPaidStatusDto));

    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {

        orderService.deleteOrder(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order Deleted");

    }
}
