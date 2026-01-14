package com.daftar.pro.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daftar.pro.entity.Menu;
import com.daftar.pro.entity.enums.RoleType;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findDistinctByRolesCodeInOrderBySortOrderAsc(Collection<RoleType> roleCodes);

	Optional<Menu> findByCodeIgnoreCase(String code);
}

