package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemQuantityAvailableException extends RuntimeException {
    public ItemQuantityAvailableException(String message) {
        super(message);
    }
}
