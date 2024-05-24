package com.salespoint.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salespoint.api.entities.RoleEntity;
import java.util.Optional;

import com.salespoint.api.utils.enums.RoleEnum;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleEnum name);
}
