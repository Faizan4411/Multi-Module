package com.daftar.pro.dto.response;

import java.util.List;
import java.util.Set;

import com.daftar.pro.dto.response.menu.MenuDto;
import com.daftar.pro.dto.response.user.UserDto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)  // force = true sets final fields to default
public class AuthResponse {

	private final String accessToken;
	private final String tokenType;
	private final long expiresIn;
	private final UserDto user;
	private final List<MenuDto> menus;
	private final Set<String> privileges;
}

