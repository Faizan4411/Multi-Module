package com.daftar.pro.dto.response.privilege;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)  // force = true sets final fields to default
public class PrivilegeDto {
	Long id;
	String code;
	String displayName;
	String description;
	Long menuId;
	String menuCode;
}
