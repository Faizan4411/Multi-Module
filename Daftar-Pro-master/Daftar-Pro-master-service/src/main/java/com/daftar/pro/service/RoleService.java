package com.daftar.pro.service;

import java.util.List;

import com.daftar.pro.dto.request.menu.AssignMenusRequest;
import com.daftar.pro.dto.request.privilege.AssignPrivilegesRequest;
import com.daftar.pro.dto.response.user.RoleDto;
import org.mapstruct.Mapper;


public interface RoleService {

	List<RoleDto> findAll();

	RoleDto assignMenus(Long roleId, AssignMenusRequest request);

	RoleDto assignPrivileges(Long roleId, AssignPrivilegesRequest request);
}
