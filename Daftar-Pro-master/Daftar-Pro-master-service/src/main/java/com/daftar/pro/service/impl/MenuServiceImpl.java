package com.daftar.pro.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.dto.request.MenuRequest;
import com.daftar.pro.dto.response.menu.MenuDto;
import com.daftar.pro.entity.Menu;
import com.daftar.pro.entity.User;
import com.daftar.pro.entity.enums.RoleType;
import com.daftar.pro.exception.BusinessException;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.mapper.MenuMapper;
import com.daftar.pro.repository.MenuRepository;
import com.daftar.pro.security.CurrentUser;
import com.daftar.pro.service.MenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final MenuMapper menuMapper;
	private final CurrentUser currentUser;

	@Override
	public List<MenuDto> getCurrentUserMenu() {
		User user = currentUser.get();
		Set<RoleType> roles = user.getRoles()
				.stream()
				.map(role -> role.getCode())
				.collect(Collectors.toSet());

		if (roles.isEmpty()) {
			return Collections.emptyList();
		}

		List<Menu> menus = menuRepository.findDistinctByRolesCodeInOrderBySortOrderAsc(roles);
		return menuMapper.toDtoList(menus);
	}

	@Override
	public List<MenuDto> findAll() {
		return menuMapper.toDtoList(menuRepository.findAll());
	}

	@Override
	public MenuDto findById(Long id) {
		return menuMapper.toDto(getMenu(id));
	}

	@Override
	@Transactional
	public MenuDto create(MenuRequest request) {
		menuRepository.findByCodeIgnoreCase(request.getCode())
				.ifPresent(menu -> {
					throw new BusinessException("Menu code already exists");
				});

		Menu menu = new Menu();
		applyRequest(menu, request);
		return menuMapper.toDto(menuRepository.save(menu));
	}

	@Override
	@Transactional
	public MenuDto update(Long id, MenuRequest request) {
		Menu menu = getMenu(id);
		if (!menu.getCode().equalsIgnoreCase(request.getCode())) {
			menuRepository.findByCodeIgnoreCase(request.getCode())
					.ifPresent(existing -> {
						throw new BusinessException("Menu code already exists");
					});
		}
		applyRequest(menu, request);
		return menuMapper.toDto(menuRepository.save(menu));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Menu menu = getMenu(id);
		menu.getRoles().forEach(role -> role.getMenus().remove(menu));
		menu.getPrivileges().forEach(privilege -> privilege.getRoles().forEach(role -> role.getPrivileges().remove(privilege)));
		menuRepository.delete(menu);
	}

	private Menu getMenu(Long id) {
		return menuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu %d not found".formatted(id)));
	}

	private void applyRequest(Menu menu, MenuRequest request) {
		menu.setCode(request.getCode().trim().toUpperCase());
		menu.setLabel(request.getLabel().trim());
		menu.setPath(request.getPath().trim());
		menu.setIcon(request.getIcon());
		menu.setSortOrder(request.getSortOrder());
		menu.setParentId(request.getParentId());
		menu.setEnabled(request.getEnabled() == null ? Boolean.TRUE : request.getEnabled());
	}
}

