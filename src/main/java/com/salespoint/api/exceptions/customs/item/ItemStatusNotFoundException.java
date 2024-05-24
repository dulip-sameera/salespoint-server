package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemStatusNotFoundException extends RuntimeException {
    public ItemStatusNotFoundException(String message) {
        super(message);
    }
}
