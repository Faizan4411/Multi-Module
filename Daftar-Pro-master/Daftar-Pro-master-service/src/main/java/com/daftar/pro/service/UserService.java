package com.daftar.pro.service;

import java.util.List;

import com.daftar.pro.dto.request.AssignRolesRequest;
import com.daftar.pro.dto.request.RegisterUserRequest;
import com.daftar.pro.dto.response.user.UserDto;

public interface UserService {

	UserDto createUser(RegisterUserRequest request);

	List<UserDto> findAll();

	UserDto findById(Long id);

	UserDto updateRoles(Long id, AssignRolesRequest request);
}

