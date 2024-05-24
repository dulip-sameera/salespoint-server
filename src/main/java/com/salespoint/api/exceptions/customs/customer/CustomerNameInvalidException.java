package com.salespoint.api.exceptions.customs.customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerNameInvalidException extends RuntimeException {
    public CustomerNameInvalidException(String message) {
        super(message);
    }
}
