package com.salespoint.api.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.salespoint.api.entities.CustomerStatusEntity;
import com.salespoint.api.repositories.CustomerStatusRepository;
import com.salespoint.api.utils.enums.CustomerStatusEnum;

@Component
public class CustomerStatusSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadStatuses();
    }

    private void loadStatuses() {
        CustomerStatusEnum[] statusNames = CustomerStatusEnum.values();

        Arrays.stream(statusNames).forEach((statusName) -> {
            Optional<CustomerStatusEntity> optionalCustomerStatus = customerStatusRepository.findByName(statusName);

            optionalCustomerStatus.ifPresentOrElse(System.out::println, () -> {

                CustomerStatusEntity customerStatus = CustomerStatusEntity
                        .builder()
                        .name(statusName)
                        .build();

                customerStatusRepository.save(customerStatus);

            });
        });
    }

}
