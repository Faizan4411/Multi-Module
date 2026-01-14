package com.daftar.pro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivilegeRequest {

	@NotBlank
	private String code;

	@NotBlank
	private String displayName;

	private String description;

	@NotNull
	private Long menuId;
}

