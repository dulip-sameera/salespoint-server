package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCategoryNotFoundException extends RuntimeException {
    public ItemCategoryNotFoundException(String message) {
        super(message);
    }
}
