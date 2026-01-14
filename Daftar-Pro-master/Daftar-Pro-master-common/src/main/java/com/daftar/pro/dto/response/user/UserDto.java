package com.daftar.pro.dto.response.user;

import java.util.Set;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)  // force = true sets final fields to default
public class UserDto {
	Long id;
	String username;
	String email;
	Boolean enabled;
	Boolean accountNonLocked;
	Set<RoleDto> roles;


}

