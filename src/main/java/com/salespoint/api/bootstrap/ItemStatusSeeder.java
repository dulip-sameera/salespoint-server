package com.salespoint.api.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.salespoint.api.entities.ItemStatusEntity;
import com.salespoint.api.repositories.ItemStatusRepository;
import com.salespoint.api.utils.enums.ItemStatusEnum;

@Component
public class ItemStatusSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ItemStatusRepository itemStatusRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadItemStatus();
    }

    private void loadItemStatus() {
        ItemStatusEnum[] statusNames = ItemStatusEnum.values();

        Arrays.stream(statusNames).forEach((statusName) -> {
            Optional<ItemStatusEntity> optionalItemStatus = itemStatusRepository.findByName(statusName);

            optionalItemStatus.ifPresentOrElse(System.out::println, () -> {

                ItemStatusEntity itemStatus = ItemStatusEntity
                        .builder()
                        .name(statusName)
                        .build();

                itemStatusRepository.save(itemStatus);

            });
        });
    }

}
