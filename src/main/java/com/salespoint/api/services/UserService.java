package com.salespoint.api.services;

import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.LoginUserDto;
import com.salespoint.api.dtos.UserDto;
import com.salespoint.api.entities.UserEntity;

@Service
public interface UserService {

    public UserEntity createUser(UserDto userDto);

    public Iterable<UserEntity> getAllUsers();

    public UserEntity getUserById(Long id);

    public UserEntity getUserByUsername(String username);

    public UserEntity updateUser(Long id, UserDto userDto);

    public void deleteUser(Long id);

    public UserEntity authenticate(LoginUserDto loginUserDto);

    public UserEntity createAdmin(UserDto userDto);
}
