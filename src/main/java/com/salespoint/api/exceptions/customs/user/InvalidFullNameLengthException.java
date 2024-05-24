package com.salespoint.api.exceptions.customs.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidFullNameLengthException extends RuntimeException {

    public InvalidFullNameLengthException(String message) {
        super(message);
    }

}
