package com.daftar.pro.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignMenusRequest {

	@NotEmpty
	private Set<Long> menuIds;
}

