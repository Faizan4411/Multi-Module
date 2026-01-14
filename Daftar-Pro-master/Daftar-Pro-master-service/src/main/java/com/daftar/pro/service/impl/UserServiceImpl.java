package com.daftar.pro.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.daftar.pro.dto.request.AssignRolesRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.response.user.UserDto;
import com.daftar.pro.entity.Role;
import com.daftar.pro.entity.User;
import com.daftar.pro.entity.enums.RoleType;
import com.daftar.pro.exception.BusinessException;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.mapper.UserMapper;
import com.daftar.pro.repository.RoleRepository;
import com.daftar.pro.repository.UserRepository;
import com.daftar.pro.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Override
	public UserDto createUser(RegisterUserRequest request) {
		validateUserUniqueness(request);
		User user = new User();
		user.setUsername(request.getUsername().trim());
		user.setEmail(request.getEmail().trim());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(resolveRoles(request.getRoles()));

		return userMapper.toDto(userRepository.save(user));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDto> findAll() {
		return userMapper.toDtoList(userRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto findById(Long id) {
		return userMapper.toDto(getUser(id));
	}

	@Override
	public UserDto updateRoles(Long id, AssignRolesRequest request) {
		User user = getUser(id);
		user.setRoles(resolveRoles(request.getRoles()));
		return userMapper.toDto(userRepository.save(user));
	}

	private void validateUserUniqueness(RegisterUserRequest request) {
		if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
			throw new BusinessException("Username is already taken");
		}

		if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
			throw new BusinessException("Email is already taken");
		}
	}

	private User getUser(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User %d not found".formatted(id)));
	}

	private Set<Role> resolveRoles(Set<String> roles) {
		Set<RoleType> roleTypes;
		if (CollectionUtils.isEmpty(roles)) {
			roleTypes = Set.of(RoleType.USER);
		}
		else {
			roleTypes = roles.stream()
					.map(role -> {
						try {
							return RoleType.valueOf(role.trim().toUpperCase());
						}
						catch (IllegalArgumentException ex) {
							throw new BusinessException("Invalid role: " + role);
						}
					})
					.collect(Collectors.toSet());
		}

		return roleTypes.stream()
				.map(this::getRoleByCode)
				.collect(Collectors.toSet());
	}

	private Role getRoleByCode(RoleType type) {
		return roleRepository.findByCode(type)
				.orElseThrow(() -> new ResourceNotFoundException("Role %s not configured".formatted(type.name())));
	}
}

