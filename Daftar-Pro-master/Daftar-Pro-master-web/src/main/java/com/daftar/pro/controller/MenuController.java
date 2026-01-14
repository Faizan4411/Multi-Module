package com.daftar.pro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daftar.pro.dto.request.MenuRequest;
import com.daftar.pro.dto.response.menu.MenuDto;
import com.daftar.pro.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@Validated
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<MenuDto> getCurrentUserMenu() {
		return menuService.getCurrentUserMenu();
	}

	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public List<MenuDto> findAll() {
		return menuService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public MenuDto findById(@PathVariable Long id) {
		return menuService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public MenuDto create(@Valid @RequestBody MenuRequest request) {
		return menuService.create(request);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public MenuDto update(@PathVariable Long id, @Valid @RequestBody MenuRequest request) {
		return menuService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public void delete(@PathVariable Long id) {
		menuService.delete(id);
	}
}

