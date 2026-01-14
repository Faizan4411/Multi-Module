package com.daftar.pro.service;

import com.daftar.pro.dto.request.AuthRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.request.TokenRefreshRequest;
import com.daftar.pro.dto.response.AuthResponse;
import com.daftar.pro.dto.response.user.UserDto;

public interface AuthService {

	AuthResponse login(AuthRequest request);

	UserDto register(RegisterUserRequest request);

	AuthResponse refresh(TokenRefreshRequest request);
}

