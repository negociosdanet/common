package com.negociosdanet.common.domain.dto;

import java.util.List;

import com.negociosdanet.common.domain.dto.PlanDto;
import com.negociosdanet.common.domain.dto.StoreDto;
import com.negociosdanet.common.domain.enumerate.SituationEnum;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;
	private String name;
	private String email;
	private SituationEnum status;
	private PlanDto plan;
	private RoleDto role;
	private List<StoreDto> stores;

	public boolean isActive() {
		return SituationEnum.ACTIVE.equals(this.status);
	}

}
