package com.daftar.pro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daftar.pro.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Optional<Privilege> findByCodeIgnoreCase(String code);

	boolean existsByCodeIgnoreCase(String code);
}

