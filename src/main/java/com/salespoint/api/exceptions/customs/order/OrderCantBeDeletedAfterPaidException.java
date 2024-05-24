package com.salespoint.api.exceptions.customs.order;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderCantBeDeletedAfterPaidException extends RuntimeException {
    public OrderCantBeDeletedAfterPaidException(String message) {
        super(message);
    }
}
