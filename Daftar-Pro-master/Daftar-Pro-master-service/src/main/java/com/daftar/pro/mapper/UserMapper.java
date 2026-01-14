package com.daftar.pro.mapper;

import java.util.List;

import org.mapstruct.Mapper;


import com.daftar.pro.dto.response.user.UserDto;
import com.daftar.pro.entity.User;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {



	public UserDto toDto(User user);


	public List<UserDto> toDtoList(List<User> users);
}


