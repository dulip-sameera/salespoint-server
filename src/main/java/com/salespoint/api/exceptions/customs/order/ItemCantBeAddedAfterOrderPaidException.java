package com.salespoint.api.exceptions.customs.order;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCantBeAddedAfterOrderPaidException extends RuntimeException {
    public ItemCantBeAddedAfterOrderPaidException(String message) {
        super(message);
    }
}
