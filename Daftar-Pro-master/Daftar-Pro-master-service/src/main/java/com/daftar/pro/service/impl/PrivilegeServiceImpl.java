package com.daftar.pro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.dto.request.PrivilegeRequest;
import com.daftar.pro.dto.response.privilege.PrivilegeDto;
import com.daftar.pro.entity.Menu;
import com.daftar.pro.entity.Privilege;
import com.daftar.pro.exception.BusinessException;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.mapper.PrivilegeMapper;
import com.daftar.pro.repository.MenuRepository;
import com.daftar.pro.repository.PrivilegeRepository;
import com.daftar.pro.service.PrivilegeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivilegeServiceImpl implements PrivilegeService {

	private final PrivilegeRepository privilegeRepository;
	private final MenuRepository menuRepository;
	private PrivilegeMapper privilegeMapper;

	@Override
	public List<PrivilegeDto> findAll() {
		return privilegeMapper.toDtoList(privilegeRepository.findAll());
	}

	@Override
	public PrivilegeDto findById(Long id) {
		return privilegeMapper.toDto(getPrivilege(id));
	}

	@Override
	@Transactional
	public PrivilegeDto create(PrivilegeRequest request) {
		String normalizedCode = normalizeCode(request.getCode());
		validateUniqueCode(normalizedCode, null);
		Privilege privilege = new Privilege();
		applyRequest(privilege, request, normalizedCode);
		return privilegeMapper.toDto(privilegeRepository.save(privilege));
	}

	@Override
	@Transactional
	public PrivilegeDto update(Long id, PrivilegeRequest request) {
		Privilege privilege = getPrivilege(id);
		String normalizedCode = normalizeCode(request.getCode());
		validateUniqueCode(normalizedCode, id);
		applyRequest(privilege, request, normalizedCode);
		return privilegeMapper.toDto(privilegeRepository.save(privilege));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Privilege privilege = getPrivilege(id);
		privilege.getRoles().forEach(role -> role.getPrivileges().remove(privilege));
		privilegeRepository.delete(privilege);
	}

	private void applyRequest(Privilege privilege, PrivilegeRequest request, String normalizedCode) {
		privilege.setCode(normalizedCode);
		privilege.setDisplayName(request.getDisplayName().trim());
		privilege.setDescription(request.getDescription() == null ? null : request.getDescription().trim());
		privilege.setMenu(getMenu(request.getMenuId()));
	}

	private Menu getMenu(Long id) {
		return menuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu %d not found".formatted(id)));
	}

	private Privilege getPrivilege(Long id) {
		return privilegeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Privilege %d not found".formatted(id)));
	}

	private void validateUniqueCode(String code, Long id) {
		privilegeRepository.findByCodeIgnoreCase(code)
				.ifPresent(existing -> {
					if (id == null || !existing.getId().equals(id)) {
						throw new BusinessException("Privilege code already exists");
					}
				});
	}

	private String normalizeCode(String code) {
		return code == null ? null : code.trim().toUpperCase();
	}
}

