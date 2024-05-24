package com.salespoint.api.exceptions.customs.customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerPhoneNumberInvalidException extends RuntimeException {

    public CustomerPhoneNumberInvalidException(String message) {
        super(message);
    }
}
