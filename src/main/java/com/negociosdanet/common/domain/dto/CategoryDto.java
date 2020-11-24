package com.negociosdanet.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CategoryDto {

	private Long categoryId;
	private String name;

	@JsonIgnore
	private StoreDto store;
}
