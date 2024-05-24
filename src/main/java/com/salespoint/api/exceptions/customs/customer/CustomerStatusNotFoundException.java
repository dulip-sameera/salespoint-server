package com.salespoint.api.exceptions.customs.customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerStatusNotFoundException extends RuntimeException {

    public CustomerStatusNotFoundException(String message) {
        super(message);
    }
}
