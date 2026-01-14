package com.daftar.pro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequest {

	@NotBlank
	private String code;

	@NotBlank
	private String label;

	@NotBlank
	private String path;

	private String icon;

	@NotNull
	private Integer sortOrder;

	private Long parentId;

	private Boolean enabled = Boolean.TRUE;
}

