package com.salespoint.api.exceptions.customs.customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
