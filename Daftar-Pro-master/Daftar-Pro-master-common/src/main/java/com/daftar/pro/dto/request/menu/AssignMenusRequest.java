package com.daftar.pro.dto.request.menu;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignMenusRequest {

	@NotNull
	private Set<Long> menuIds;
}

