package com.daftar.pro.dto.response.menu;

import java.util.Set;

import com.daftar.pro.dto.response.privilege.PrivilegeDto;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)  // force = true sets final fields to default
public class MenuDto {
	Long id;
	String code;
	String label;
	String path;
	String icon;
	Integer sortOrder;
	Long parentId;
	Boolean enabled;
	Set<PrivilegeDto> privileges;
}

