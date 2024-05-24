package com.salespoint.api.exceptions.customs.stock;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
