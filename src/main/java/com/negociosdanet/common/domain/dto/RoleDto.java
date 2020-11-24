package com.negociosdanet.common.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RoleDto {

	private Long roleId;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	private String description;

	@JsonIgnore
	private List<PermissionDto> permissions;

}
