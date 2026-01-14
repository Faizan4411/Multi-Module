package com.daftar.pro.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.daftar.pro.dto.response.user.RoleDto;
import com.daftar.pro.entity.Role;

@Mapper(componentModel = "spring", uses = {MenuMapper.class, PrivilegeMapper.class})
public interface RoleMapper {

	RoleDto toDto(Role role);

	List<RoleDto> toDtoList(List<Role> roles);
}

