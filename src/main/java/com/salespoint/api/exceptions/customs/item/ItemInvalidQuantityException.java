package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemInvalidQuantityException extends RuntimeException {
    public ItemInvalidQuantityException(String message) {
        super(message);
    }
}
