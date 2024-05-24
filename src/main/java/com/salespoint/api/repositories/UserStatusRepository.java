package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.UserStatusEntity;
import java.util.Optional;

import com.salespoint.api.utils.enums.UserStatusEnum;

@Repository
public interface UserStatusRepository extends CrudRepository<UserStatusEntity, Integer> {
    Optional<UserStatusEntity> findByName(UserStatusEnum name);
}
