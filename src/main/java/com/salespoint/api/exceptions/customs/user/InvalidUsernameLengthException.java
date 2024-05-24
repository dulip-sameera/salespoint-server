package com.salespoint.api.exceptions.customs.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidUsernameLengthException extends RuntimeException {
    public InvalidUsernameLengthException(String message) {
        super(message);
    }
}
