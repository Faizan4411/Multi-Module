package com.daftar.pro.service;

import java.util.List;

import com.daftar.pro.dto.request.MenuRequest;
import com.daftar.pro.dto.response.menu.MenuDto;

public interface MenuService {

	List<MenuDto> getCurrentUserMenu();

	List<MenuDto> findAll();

	MenuDto findById(Long id);

	MenuDto create(MenuRequest request);

	MenuDto update(Long id, MenuRequest request);

	void delete(Long id);
}

