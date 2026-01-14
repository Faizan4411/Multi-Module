package com.daftar.pro.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.dto.request.AuthRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.request.TokenRefreshRequest;
import com.daftar.pro.dto.response.AuthResponse;
import com.daftar.pro.dto.response.user.UserDto;
import com.daftar.pro.entity.Privilege;
import com.daftar.pro.entity.User;
import com.daftar.pro.exception.BusinessException;
import com.daftar.pro.exception.ResourceNotFoundException;
import com.daftar.pro.mapper.UserMapper;
import com.daftar.pro.repository.UserRepository;
import com.daftar.pro.security.JwtTokenProvider;
import com.daftar.pro.service.AuthService;
import com.daftar.pro.service.MenuService;
import com.daftar.pro.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final MenuService menuService;
	private final UserService userService;

	@Override
	@Transactional(readOnly = true)
	public AuthResponse login(AuthRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtTokenProvider.generateToken(userDetails);
		User user = findUser(authentication.getName());

		return AuthResponse.builder()
				.accessToken(token)
				.tokenType("Bearer")
				.expiresIn(jwtTokenProvider.getExpirationInMs())
				.user(userMapper.toDto(user))

				.menus(menuService.getCurrentUserMenu())
				.privileges(extractPrivilegeCodes(user))
				.build();
	}

	@Override
	@Transactional
	public UserDto register(RegisterUserRequest request) {
		return userService.createUser(request);
	}

	@Override
	@Transactional(readOnly = true)
	public AuthResponse refresh(TokenRefreshRequest request) {
		String username = jwtTokenProvider.getUsernameFromToken(request.getRefreshToken());
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		if (!jwtTokenProvider.isTokenValid(request.getRefreshToken(), userDetails)) {
			throw new BusinessException("Refresh token is invalid or expired");
		}

		String token = jwtTokenProvider.generateToken(userDetails);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = findUser(username);

		return AuthResponse.builder()
				.accessToken(token)
				.tokenType("Bearer")
				.expiresIn(jwtTokenProvider.getExpirationInMs())
				.user(userMapper.toDto(user))
				.menus(menuService.getCurrentUserMenu())
				.privileges(extractPrivilegeCodes(user))
				.build();
	}

	private User findUser(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new ResourceNotFoundException("User %s not found".formatted(username)));
	}

	private Set<String> extractPrivilegeCodes(User user) {
		return user.getRoles()
				.stream()
				.flatMap(role -> role.getPrivileges().stream())
				.map(Privilege::getCode)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
}

