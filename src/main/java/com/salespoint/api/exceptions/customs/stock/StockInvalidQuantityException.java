package com.salespoint.api.exceptions.customs.stock;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StockInvalidQuantityException extends RuntimeException {
    public StockInvalidQuantityException(String message) {
        super(message);
    }
}
