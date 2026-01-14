package com.daftar.pro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.daftar.pro.dto.response.privilege.PrivilegeDto;
import com.daftar.pro.entity.Privilege;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

//	@Mapping(source = "menu.id", target = "menuId")
//	@Mapping(source = "menu.code", target = "menuCode")
	PrivilegeDto toDto(Privilege privilege);

	List<PrivilegeDto> toDtoList(List<Privilege> privileges);
}


