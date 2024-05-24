package com.salespoint.api.exceptions.customs.order;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCantBeRemovedAfterOrderPaidException extends RuntimeException {
    public ItemCantBeRemovedAfterOrderPaidException(String message) {
        super(message);
    }
}
