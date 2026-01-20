package com.psp.sentinel.repository;

import com.psp.sentinel.model.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<ServerEntity, String> {
    Optional<ServerEntity> findByName(String name);
}
