package com.horizon.bartersys.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bartersys.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

