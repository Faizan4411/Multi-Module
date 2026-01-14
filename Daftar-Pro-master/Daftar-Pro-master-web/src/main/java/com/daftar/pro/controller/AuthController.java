package com.daftar.pro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daftar.pro.dto.request.AuthRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.request.TokenRefreshRequest;
import com.daftar.pro.dto.response.AuthResponse;
import com.daftar.pro.dto.response.user.UserDto;
import com.daftar.pro.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody AuthRequest request) {
		return authService.login(request);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto register(@Valid @RequestBody RegisterUserRequest request) {
		return authService.register(request);
	}

	@PostMapping("/refresh")
	public AuthResponse refresh(@Valid @RequestBody TokenRefreshRequest request) {
		return authService.refresh(request);
	}
}

