package com.salespoint.api.bootstrap;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salespoint.api.dtos.UserDto;
import com.salespoint.api.entities.RoleEntity;
import com.salespoint.api.entities.UserEntity;
import com.salespoint.api.entities.UserStatusEntity;
import com.salespoint.api.repositories.RoleRepository;
import com.salespoint.api.repositories.UserRepository;
import com.salespoint.api.repositories.UserStatusRepository;
import com.salespoint.api.utils.enums.RoleEnum;
import com.salespoint.api.utils.enums.UserStatusEnum;

@Component
public class SuperAdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createAdmin();
    }

    private void createAdmin() {
        UserDto userDto = UserDto
                .builder()
                .fullName("Super Admin")
                .username("superadmin")
                .password("123456")
                .build();

        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());
        Optional<UserStatusEntity> optionalStatus = userStatusRepository.findByName(UserStatusEnum.ACTIVE);

        if (optionalRole.isEmpty() || optionalUser.isPresent() || optionalStatus.isEmpty()) {
            return;
        }

        UserEntity user = UserEntity
                .builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(optionalRole.get())
                .status(optionalStatus.get())
                .build();

        userRepository.save(user);
    }

}
