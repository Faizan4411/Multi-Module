package com.daftar.pro.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.dto.request.menu.AssignMenusRequest;
import com.daftar.pro.dto.request.privilege.AssignPrivilegesRequest;
import com.daftar.pro.dto.response.user.RoleDto;
import com.daftar.pro.entity.Menu;
import com.daftar.pro.entity.Privilege;
import com.daftar.pro.entity.Role;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.mapper.RoleMapper;
import com.daftar.pro.repository.MenuRepository;
import com.daftar.pro.repository.PrivilegeRepository;
import com.daftar.pro.repository.RoleRepository;
import com.daftar.pro.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final MenuRepository menuRepository;
	private final PrivilegeRepository privilegeRepository;
	private final RoleMapper roleMapper;

	@Override
	public List<RoleDto> findAll() {
		return roleMapper.toDtoList(roleRepository.findAll());
	}

	@Override
	@Transactional
	public RoleDto assignMenus(Long roleId, AssignMenusRequest request) {
		Role role = getRole(roleId);
		Set<Menu> menus = defaultIfNull(request.getMenuIds())
				.stream()
				.map(this::getMenu)
				.collect(Collectors.toSet());

		role.setMenus(menus);
		return roleMapper.toDto(roleRepository.save(role));
	}

	@Override
	@Transactional
	public RoleDto assignPrivileges(Long roleId, AssignPrivilegesRequest request) {
		Role role = getRole(roleId);
		Set<Privilege> privileges = defaultIfNull(request.getPrivilegeIds())
				.stream()
				.map(this::getPrivilege)
				.collect(Collectors.toSet());

		role.setPrivileges(privileges);
		return roleMapper.toDto(roleRepository.save(role));
	}

	private Role getRole(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role %d not found".formatted(id)));
	}

	private Menu getMenu(Long id) {
		return menuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu %d not found".formatted(id)));
	}

	private Privilege getPrivilege(Long id) {
		return privilegeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Privilege %d not found".formatted(id)));
	}

	private <T> Set<T> defaultIfNull(Set<T> values) {
		return values == null ? Set.of() : values;
	}
}
