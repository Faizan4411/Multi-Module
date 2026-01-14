package com.daftar.pro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daftar.pro.entity.Role;
import com.daftar.pro.entity.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByCode(RoleType code);
}

