package com.negociosdanet.common.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PlanDto {

	private Long planId;

	@NotBlank(message = "Name is mandatory")
	private String name;
	private String description;

	@NotNull(message = "Price is mandatory")
	@Min(value = 0, message = "The price must be greater than 0")
	@Max(value = 9999, message = "the price must be less than 9999")
	private BigDecimal price;

}
