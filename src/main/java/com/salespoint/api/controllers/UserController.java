package com.salespoint.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salespoint.api.dtos.UserDto;
import com.salespoint.api.entities.UserEntity;
import com.salespoint.api.exceptions.customs.user.InvalidFullNameLengthException;
import com.salespoint.api.exceptions.customs.user.InvalidPasswordLengthException;
import com.salespoint.api.exceptions.customs.user.InvalidUsernameLengthException;
import com.salespoint.api.services.UserService;
import com.salespoint.api.utils.enums.RoleEnum;
import com.salespoint.api.utils.enums.UserStatusEnum;
import com.salespoint.api.utils.response.UserResponse;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private int ACCEPTABLE_USERNAME_LENGTH = 3;
    private int ACCEPTABLE_FULL_NAME_LENGTH = 3;
    private int ACCEPTABLE_PASSWORD_LENGTH = 8;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        Iterable<UserEntity> userEntities = userService.getAllUsers();

        List<UserResponse> userResponses = new ArrayList<>();

        userEntities.forEach((userEntity) -> {

            UserResponse userResponse = UserResponse
                    .builder()
                    .id(userEntity.getId())
                    .fullName(userEntity.getFullName())
                    .username(userEntity.getUsername())
                    .role(userEntity.getRole().getName().toString())
                    .status(userEntity.getStatus().getName().toString())
                    .isAccountNonExpired(userEntity.isAccountNonExpired())
                    .isAccountNonLocked(userEntity.isAccountNonLocked())
                    .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                    .isEnabled(userEntity.isEnabled())
                    .build();

            userResponses.add(userResponse);

        });

        return ResponseEntity.ok(userResponses);

    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();

        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/statuses")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER','CLERK','CASHIER')")
    public ResponseEntity<List<String>> getAllStatuses() {
        List<String> statuses = new ArrayList<>();

        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            statuses.add(statusEnum.name());
        }

        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER','CLERK','CASHIER')")
    public ResponseEntity<List<String>> getAllUserRoles() {
        List<String> statuses = new ArrayList<>();

        for (RoleEnum statusEnum : RoleEnum.values()) {
            statuses.add(statusEnum.name());
        }

        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();

        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/find/{username}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        UserEntity userEntity = userService.getUserByUsername(username);

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody UserDto userDto) {

        // if exists, remove leading and trailing white spaces
        userDto.setFullName(userDto.getFullName().trim());
        userDto.setUsername(userDto.getUsername().trim());
        userDto.setPassword(userDto.getPassword().trim());

        if (userDto.getFullName().length() < ACCEPTABLE_FULL_NAME_LENGTH) {
            throw new InvalidFullNameLengthException("Full Name's length is less than " + ACCEPTABLE_FULL_NAME_LENGTH);
        }

        if (userDto.getUsername().length() < ACCEPTABLE_USERNAME_LENGTH) {
            throw new InvalidUsernameLengthException("Username's length is less than " + ACCEPTABLE_USERNAME_LENGTH);
        }

        if (userDto.getPassword().length() < ACCEPTABLE_PASSWORD_LENGTH) {
            throw new InvalidPasswordLengthException("Password's length is less than " + ACCEPTABLE_PASSWORD_LENGTH);
        }

        UserEntity userEntity = userService.createAdmin(userDto);

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDto userDto) {

        // if exists, remove leading and trailing white spaces
        userDto.setFullName(userDto.getFullName().trim());
        userDto.setUsername(userDto.getUsername().trim());
        userDto.setPassword(userDto.getPassword().trim());

        if (userDto.getFullName().length() < ACCEPTABLE_FULL_NAME_LENGTH) {
            throw new InvalidFullNameLengthException("Full Name's length is less than " + ACCEPTABLE_FULL_NAME_LENGTH);
        }

        if (userDto.getUsername().length() < ACCEPTABLE_USERNAME_LENGTH) {
            throw new InvalidUsernameLengthException("Username's length is less than " + ACCEPTABLE_USERNAME_LENGTH);
        }

        if (userDto.getPassword().length() < ACCEPTABLE_PASSWORD_LENGTH) {
            throw new InvalidPasswordLengthException("Password's length is less than " + ACCEPTABLE_PASSWORD_LENGTH);
        }

        UserEntity userEntity = userService.createUser(userDto);

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        // if exists, remove leading and trailing white spaces
        userDto.setFullName(userDto.getFullName().trim());
        userDto.setUsername(userDto.getUsername().trim());

        if (userDto.getFullName().length() < ACCEPTABLE_FULL_NAME_LENGTH) {
            throw new InvalidFullNameLengthException("Full Name's length is less than " + ACCEPTABLE_FULL_NAME_LENGTH);
        }

        if (userDto.getUsername().length() < ACCEPTABLE_USERNAME_LENGTH) {
            throw new InvalidUsernameLengthException("Username's length is less than " + ACCEPTABLE_USERNAME_LENGTH);
        }

        if (userDto.getPassword() != null) {
            userDto.setPassword(userDto.getPassword().trim());

            if (userDto.getPassword().length() < ACCEPTABLE_PASSWORD_LENGTH) {
                throw new InvalidPasswordLengthException(
                        "Password's length is less than " + ACCEPTABLE_PASSWORD_LENGTH);
            }
        }

        UserEntity userEntity = userService.updateUser(id, userDto);

        UserResponse userResponse = UserResponse
                .builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getName().toString())
                .status(userEntity.getStatus().getName().toString())
                .isAccountNonExpired(userEntity.isAccountNonExpired())
                .isAccountNonLocked(userEntity.isAccountNonLocked())
                .isCredentialsNonExpired(userEntity.isCredentialsNonExpired())
                .isEnabled(userEntity.isEnabled())
                .build();
        return ResponseEntity.ok(userResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted");
    }
}
