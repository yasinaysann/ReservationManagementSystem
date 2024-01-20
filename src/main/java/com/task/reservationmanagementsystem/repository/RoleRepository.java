package com.task.reservationmanagementsystem.repository;

import com.task.reservationmanagementsystem.entity.Role;
import com.task.reservationmanagementsystem.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Roles name);
}
