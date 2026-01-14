package com.daftar.pro.dto.request.privilege;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignPrivilegesRequest {

	@NotNull
	private Set<Long> privilegeIds;
}

