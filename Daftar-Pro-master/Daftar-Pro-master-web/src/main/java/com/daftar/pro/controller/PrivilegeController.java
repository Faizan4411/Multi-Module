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

import com.daftar.pro.dto.request.PrivilegeRequest;
import com.daftar.pro.dto.response.privilege.PrivilegeDto;
import com.daftar.pro.service.PrivilegeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/privileges")
@Validated
@RequiredArgsConstructor
public class PrivilegeController {

	private final PrivilegeService privilegeService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public List<PrivilegeDto> findAll() {
		return privilegeService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public PrivilegeDto findById(@PathVariable Long id) {
		return privilegeService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public PrivilegeDto create(@Valid @RequestBody PrivilegeRequest request) {
		return privilegeService.create(request);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public PrivilegeDto update(@PathVariable Long id, @Valid @RequestBody PrivilegeRequest request) {
		return privilegeService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public void delete(@PathVariable Long id) {
		privilegeService.delete(id);
	}
}
