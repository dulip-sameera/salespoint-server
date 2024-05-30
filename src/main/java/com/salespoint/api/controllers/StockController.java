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

import com.salespoint.api.dtos.StockDto;
import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.entities.StockEntity;
import com.salespoint.api.exceptions.customs.stock.StockInvalidQuantityException;
import com.salespoint.api.services.StockService;
import com.salespoint.api.utils.response.ItemResponse;
import com.salespoint.api.utils.response.StockResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    private Integer ACCEPTABLE_MINIMUM_QUANTITY = 1;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<StockResponse>> getAllStocks() {

        Iterable<StockEntity> stocks = stockService.getAllStocks();

        List<StockResponse> stockResponses = new ArrayList<>();

        stocks.forEach((stock) -> {

            ItemEntity item = stock.getItem();

            ItemResponse itemResponse = ItemResponse
                    .builder()
                    .id(item.getId())
                    .name(item.getName())
                    .unitPrice(item.getUnitPrice())
                    .qty(item.getQty())
                    .status(item.getStatus().toString())
                    .category(item.getItemCategory().getName())
                    .createdAt(item.getCreatedAt())
                    .updatedAt(item.getUpdatedAt())
                    .build();

            StockResponse stockResponse = StockResponse
                    .builder()
                    .id(stock.getId())
                    .qty(stock.getQty())
                    .createdAt(stock.getCreatedAt())
                    .updatedAt(stock.getUpdatedAt())
                    .item(itemResponse)
                    .build();

            stockResponses.add(stockResponse);

        });

        return ResponseEntity.ok(stockResponses);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<StockResponse> getStockById(@PathVariable Long id) {
        StockEntity stock = stockService.getStockById(id);

        ItemEntity item = stock.getItem();

        ItemResponse itemResponse = ItemResponse
                .builder()
                .id(item.getId())
                .name(item.getName())
                .unitPrice(item.getUnitPrice())
                .qty(item.getQty())
                .status(item.getStatus().toString())
                .category(item.getItemCategory().getName())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();

        StockResponse stockResponse = StockResponse
                .builder()
                .id(stock.getId())
                .qty(stock.getQty())
                .createdAt(stock.getCreatedAt())
                .updatedAt(stock.getUpdatedAt())
                .item(itemResponse)
                .build();

        return ResponseEntity.ok(stockResponse);

    }

    @GetMapping("/item/{itemId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<StockResponse>> getStocksByItem(@PathVariable Long itemId) {

        List<StockEntity> stocks = stockService.getStocksByItem(itemId);

        List<StockResponse> stockResponses = new ArrayList<>();

        stocks.forEach((stock) -> {

            ItemEntity item = stock.getItem();

            ItemResponse itemResponse = ItemResponse
                    .builder()
                    .id(item.getId())
                    .name(item.getName())
                    .unitPrice(item.getUnitPrice())
                    .qty(item.getQty())
                    .status(item.getStatus().toString())
                    .category(item.getItemCategory().getName())
                    .createdAt(item.getCreatedAt())
                    .updatedAt(item.getUpdatedAt())
                    .build();

            StockResponse stockResponse = StockResponse
                    .builder()
                    .id(stock.getId())
                    .qty(stock.getQty())
                    .createdAt(stock.getCreatedAt())
                    .updatedAt(stock.getUpdatedAt())
                    .item(itemResponse)
                    .build();

            stockResponses.add(stockResponse);

        });

        return ResponseEntity.ok(stockResponses);

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<StockResponse> createStock(@RequestBody StockDto stockDto) {

        // validations
        if (stockDto.getQty() < ACCEPTABLE_MINIMUM_QUANTITY) {
            throw new StockInvalidQuantityException();
        }

        StockEntity createdStock = stockService.createStock(stockDto);

        ItemEntity item = createdStock.getItem();

        ItemResponse itemResponse = ItemResponse
                .builder()
                .id(item.getId())
                .name(item.getName())
                .unitPrice(item.getUnitPrice())
                .qty(item.getQty())
                .status(item.getStatus().toString())
                .category(item.getItemCategory().getName())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();

        StockResponse stockResponse = StockResponse
                .builder()
                .id(createdStock.getId())
                .qty(createdStock.getQty())
                .createdAt(createdStock.getCreatedAt())
                .updatedAt(createdStock.getUpdatedAt())
                .item(itemResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(stockResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<StockResponse> updateStock(@PathVariable Long id, @RequestBody StockDto stockDto) {

        // validations
        if (stockDto.getQty() < ACCEPTABLE_MINIMUM_QUANTITY) {
            throw new StockInvalidQuantityException();
        }

        StockEntity updatedStock = stockService.updateStock(id, stockDto);

        ItemEntity item = updatedStock.getItem();

        ItemResponse itemResponse = ItemResponse
                .builder()
                .id(item.getId())
                .name(item.getName())
                .unitPrice(item.getUnitPrice())
                .qty(item.getQty())
                .status(item.getStatus().toString())
                .category(item.getItemCategory().getName())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();

        StockResponse stockResponse = StockResponse
                .builder()
                .id(updatedStock.getId())
                .qty(updatedStock.getQty())
                .createdAt(updatedStock.getCreatedAt())
                .updatedAt(updatedStock.getUpdatedAt())
                .item(itemResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(stockResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Stock Deleted");
    }

}
