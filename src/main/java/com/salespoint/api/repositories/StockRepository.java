package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.StockEntity;
import java.util.List;
import java.util.Optional;

import com.salespoint.api.entities.ItemEntity;

@Repository
public interface StockRepository extends CrudRepository<StockEntity, Long> {
    Optional<List<StockEntity>> findAllByItem(ItemEntity item);
}
