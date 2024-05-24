package com.salespoint.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.CustomerEntity;
import com.salespoint.api.entities.OrderEntity;
import com.salespoint.api.entities.UserEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Optional<List<OrderEntity>> findAllByCustomer(CustomerEntity customer);

    Optional<List<OrderEntity>> findAllByCreatedBy(UserEntity user);

}
