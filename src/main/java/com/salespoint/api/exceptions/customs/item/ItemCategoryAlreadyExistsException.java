package com.salespoint.api.exceptions.customs.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCategoryAlreadyExistsException extends RuntimeException {
    public ItemCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
