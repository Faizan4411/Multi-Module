package com.daftar.pro.service;

import java.util.List;

import com.daftar.pro.dto.request.PrivilegeRequest;
import com.daftar.pro.dto.response.privilege.PrivilegeDto;



public interface PrivilegeService {

	List<PrivilegeDto> findAll();

	PrivilegeDto findById(Long id);

	PrivilegeDto create(PrivilegeRequest request);

	PrivilegeDto update(Long id, PrivilegeRequest request);

	void delete(Long id);
}

