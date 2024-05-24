package com.salespoint.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.LoginUserDto;
import com.salespoint.api.dtos.UserDto;
import com.salespoint.api.entities.RoleEntity;
import com.salespoint.api.entities.UserEntity;
import com.salespoint.api.entities.UserStatusEntity;
import com.salespoint.api.exceptions.customs.user.UsernameAlreadyExistsException;
import com.salespoint.api.exceptions.customs.user.UserNotFoundException;
import com.salespoint.api.exceptions.customs.user.UserRoleNotFoundException;
import com.salespoint.api.exceptions.customs.user.UserStatusNotFoundException;
import com.salespoint.api.repositories.RoleRepository;
import com.salespoint.api.repositories.UserRepository;
import com.salespoint.api.repositories.UserStatusRepository;
import com.salespoint.api.services.UserService;
import com.salespoint.api.utils.enums.RoleEnum;
import com.salespoint.api.utils.enums.UserStatusEnum;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity createUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        UserStatusEntity status = userStatusRepository.findByName(UserStatusEnum.ACTIVE)
                .orElseThrow(() -> new UserStatusNotFoundException());

        RoleEnum userRoleEnum = null;

        RoleEnum[] roleEnums = RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {

            if (roleEnum.equals(RoleEnum.SUPER_ADMIN) || roleEnum.equals(RoleEnum.ADMIN)) {
                continue;
            }

            if (userDto.getRole().equals(roleEnum.name())) {
                userRoleEnum = roleEnum;
            }
        }

        RoleEntity role = roleRepository.findByName(userRoleEnum).orElseThrow(() -> new UserRoleNotFoundException());

        UserEntity user = UserEntity.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .status(status)
                .role(role)
                .build();

        return userRepository.save(user);

    }

    @Override
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserEntity updateUser(Long id, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        UserStatusEnum statusEnum = userDto.getStatus().equals(UserStatusEnum.ACTIVE.toString()) ? UserStatusEnum.ACTIVE
                : UserStatusEnum.DELETE;

        UserStatusEntity status = userStatusRepository.findByName(statusEnum)
                .orElseThrow(() -> new UserStatusNotFoundException());

        RoleEnum userRoleEnum = null;

        RoleEnum[] roleEnums = RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {

            if (roleEnum.equals(RoleEnum.SUPER_ADMIN) || roleEnum.equals(RoleEnum.ADMIN)) {
                continue;
            }

            if (userDto.getRole().equals(roleEnum.name())) {
                userRoleEnum = roleEnum;
            }
        }

        RoleEntity role = roleRepository.findByName(userRoleEnum).orElseThrow(() -> new UserRoleNotFoundException());

        String password = existingUser.getPassword();

        if (userDto.getPassword() != null) {
            password = passwordEncoder.encode(userDto.getPassword());
        }

        existingUser.setFullName(userDto.getFullName());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setStatus(status);
        existingUser.setPassword(password);
        existingUser.setRole(role);

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        UserStatusEntity status = userStatusRepository.findByName(UserStatusEnum.DELETE)
                .orElseThrow(() -> new UserStatusNotFoundException());

        existingUser.setStatus(status);

        userRepository.save(existingUser);
    }

    @Override
    public UserEntity authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        return userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserEntity createAdmin(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        UserStatusEntity status = userStatusRepository.findByName(UserStatusEnum.ACTIVE)
                .orElseThrow(() -> new UserStatusNotFoundException());

        RoleEntity role = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(() -> new UserRoleNotFoundException());

        UserEntity user = UserEntity.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .status(status)
                .role(role)
                .build();

        return userRepository.save(user);
    }

}
