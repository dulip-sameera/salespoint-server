package com.salespoint.api.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.salespoint.api.entities.UserStatusEntity;
import com.salespoint.api.repositories.UserStatusRepository;
import com.salespoint.api.utils.enums.UserStatusEnum;

@Component
public class UserStatusSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadStatuses();
    }

    private void loadStatuses() {
        UserStatusEnum[] statusNames = UserStatusEnum.values();

        Arrays.stream(statusNames).forEach((statusName) -> {
            Optional<UserStatusEntity> optionalUserStatus = userStatusRepository.findByName(statusName);

            optionalUserStatus.ifPresentOrElse(System.out::println, () -> {

                UserStatusEntity userStatus = UserStatusEntity
                        .builder()
                        .name(statusName)
                        .build();

                userStatusRepository.save(userStatus);

            });
        });
    }

}
