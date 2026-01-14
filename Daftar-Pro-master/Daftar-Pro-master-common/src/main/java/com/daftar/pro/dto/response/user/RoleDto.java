package com.daftar.pro.dto.response.user;

import java.util.Set;

import com.daftar.pro.dto.response.menu.MenuDto;
import com.daftar.pro.dto.response.privilege.PrivilegeDto;
import com.daftar.pro.entity.enums.RoleType;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)  // force = true sets final fields to default
public class RoleDto {
	Long id;
	RoleType code;
	String displayName;
	String description;
	Set<MenuDto> menus;
	Set<PrivilegeDto> privileges;
}

