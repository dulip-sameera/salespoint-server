package com.salespoint.api.exceptions.customs.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameInvalidException extends RuntimeException {
    public UsernameInvalidException(String message) {
        super(message);
    }
}
