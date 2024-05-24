package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.ItemStatusEntity;
import java.util.Optional;

import com.salespoint.api.utils.enums.ItemStatusEnum;

@Repository
public interface ItemStatusRepository extends CrudRepository<ItemStatusEntity, Integer> {
    Optional<ItemStatusEntity> findByName(ItemStatusEnum name);
}
