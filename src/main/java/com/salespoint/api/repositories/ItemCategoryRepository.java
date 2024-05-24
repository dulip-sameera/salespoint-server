package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.ItemCategoryEntity;
import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends CrudRepository<ItemCategoryEntity, Integer> {
    Optional<ItemCategoryEntity> findByName(String name);
}
