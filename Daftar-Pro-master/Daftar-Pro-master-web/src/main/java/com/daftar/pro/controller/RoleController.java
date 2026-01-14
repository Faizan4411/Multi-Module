package com.daftar.pro.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daftar.pro.dto.request.menu.AssignMenusRequest;
import com.daftar.pro.dto.request.privilege.AssignPrivilegesRequest;
import com.daftar.pro.dto.response.user.RoleDto;
import com.daftar.pro.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@Validated
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public List<RoleDto> findAll() {
		return roleService.findAll();
	}

	@PutMapping("/{roleId}/menus")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public RoleDto assignMenus(@PathVariable Long roleId, @Valid @RequestBody AssignMenusRequest request) {
		return roleService.assignMenus(roleId, request);
	}

	@PutMapping("/{roleId}/privileges")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public RoleDto assignPrivileges(@PathVariable Long roleId, @Valid @RequestBody AssignPrivilegesRequest request) {
		return roleService.assignPrivileges(roleId, request);
	}
}
