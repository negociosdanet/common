package com.negociosdanet.common.domain.enumerate;

import lombok.Getter;

@Getter
public enum SituationEnum {
	ACTIVE(0, "Ativo"), INACTIVE(1, "Inativo");

	private Integer status;
	private String description;

	SituationEnum(int statusCode, String statusDescription) {
		this.status = statusCode;
		this.description = statusDescription;
	}

}
