package com.negociosdanet.common.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PermissionDto {

	private Long permissionId;

	@NotBlank(message = "Name is mandatory")
	private String name;
	private String description;
	
	@NotNull(message = "Role is mandatory")
	private RoleDto role;

}
