package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.CustomerStatusEntity;
import java.util.Optional;

import com.salespoint.api.utils.enums.CustomerStatusEnum;

@Repository
public interface CustomerStatusRepository extends CrudRepository<CustomerStatusEntity, Integer> {
    Optional<CustomerStatusEntity> findByName(CustomerStatusEnum name);
}
