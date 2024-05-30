package com.salespoint.api.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.salespoint.api.dtos.ItemDto;
import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.exceptions.customs.item.ItemInvalidQuantityException;
import com.salespoint.api.exceptions.customs.item.ItemPriceInvalidException;
import com.salespoint.api.services.ItemService;
import com.salespoint.api.utils.enums.ItemStatusEnum;
import com.salespoint.api.utils.response.ItemResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        Iterable<ItemEntity> items = itemService.getAllItems();

        List<ItemResponse> itemResponses = new ArrayList<>();

        items.forEach((item) -> {

            ItemResponse itemResponse = ItemResponse
                    .builder()
                    .id(item.getId())
                    .name(item.getName())
                    .unitPrice(item.getUnitPrice())
                    .qty(item.getQty())
                    .status(item.getStatus().getName().toString())
                    .category(item.getItemCategory().getName())
                    .createdAt(item.getCreatedAt())
                    .updatedAt(item.getUpdatedAt())
                    .build();

            itemResponses.add(itemResponse);

        });

        return ResponseEntity.ok(itemResponses);

    }

    @GetMapping("/statuses")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<String>> getAllStatuses() {
        List<String> statuses = new ArrayList<>();

        for (ItemStatusEnum statusEnum : ItemStatusEnum.values()) {
            statuses.add(statusEnum.name());
        }

        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        ItemEntity item = itemService.getItemById(id);
        ItemResponse itemResponse = ItemResponse
                .builder()
                .id(item.getId())
                .name(item.getName())
                .unitPrice(item.getUnitPrice())
                .qty(item.getQty())
                .status(item.getStatus().getName().toString())
                .category(item.getItemCategory().getName())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();

        return ResponseEntity.ok(itemResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemDto itemDto) {
        // If exists, remove leading and trailing white spaces
        itemDto.setName(itemDto.getName().trim());

        // validation

        if (itemDto.getUnitPrice().signum() < 0) {
            throw new ItemPriceInvalidException();
        }

        ItemEntity createdItem = itemService.createItem(itemDto);

        ItemResponse createdItemResponse = ItemResponse
                .builder()
                .id(createdItem.getId())
                .name(createdItem.getName())
                .unitPrice(createdItem.getUnitPrice())
                .qty(createdItem.getQty())
                .status(createdItem.getStatus().getName().toString())
                .category(createdItem.getItemCategory().getName())
                .createdAt(createdItem.getCreatedAt())
                .updatedAt(createdItem.getUpdatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createdItemResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        // If exists, remove leading and trailing white spaces
        itemDto.setName(itemDto.getName().trim());

        // validation
        if (itemDto.getUnitPrice().signum() < 0) {
            throw new ItemPriceInvalidException();
        }

        ItemEntity updatedItem = itemService.updateItem(id, itemDto);

        ItemResponse updatedItemResponse = ItemResponse
                .builder()
                .id(updatedItem.getId())
                .name(updatedItem.getName())
                .unitPrice(updatedItem.getUnitPrice())
                .qty(updatedItem.getQty())
                .status(updatedItem.getStatus().getName().toString())
                .category(updatedItem.getItemCategory().getName())
                .createdAt(updatedItem.getCreatedAt())
                .updatedAt(updatedItem.getUpdatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(updatedItemResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {

        itemService.deleteItem(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item Deleted");
    }

}
