package com.salespoint.api.exceptions.customs.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
