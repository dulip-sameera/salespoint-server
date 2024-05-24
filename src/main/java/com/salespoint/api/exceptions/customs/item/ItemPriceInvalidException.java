package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemPriceInvalidException extends RuntimeException {
    public ItemPriceInvalidException(String message) {
        super(message);
    }
}
