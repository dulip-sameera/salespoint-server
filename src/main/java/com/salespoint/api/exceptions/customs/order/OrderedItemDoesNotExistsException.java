package com.salespoint.api.exceptions.customs.order;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderedItemDoesNotExistsException extends RuntimeException {
    public OrderedItemDoesNotExistsException(String message) {
        super(message);
    }
}
