package com.daftar.pro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daftar.pro.dto.request.AssignRolesRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.response.user.UserDto;
import com.daftar.pro.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public List<UserDto> findAll() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public UserDto findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public UserDto create(@Valid @RequestBody RegisterUserRequest request) {
		return userService.createUser(request);
	}

	@PutMapping("/{id}/roles")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public UserDto updateRoles(@PathVariable Long id, @Valid @RequestBody AssignRolesRequest request) {
		return userService.updateRoles(id, request);
	}
}

