package com.daftar.pro.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.daftar.pro.dto.response.menu.MenuDto;
import com.daftar.pro.entity.Menu;

@Mapper(componentModel = "spring", uses = PrivilegeMapper.class)
public interface MenuMapper {

	MenuDto toDto(Menu menu);

	List<MenuDto> toDtoList(List<Menu> menus);
}

