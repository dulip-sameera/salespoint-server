package com.salespoint.api.exceptions.customs.stock;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException(String message) {
        super(message);
    }
}
