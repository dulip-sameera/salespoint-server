package com.salespoint.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.entities.OrderEntity;
import com.salespoint.api.entities.OrderItemEntity;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {

    Optional<OrderItemEntity> findByOrderAndItem(OrderEntity order, ItemEntity item);

    Optional<List<OrderItemEntity>> findAllByOrder(OrderEntity order);

    Optional<List<OrderItemEntity>> findAllByItem(ItemEntity item);

}
