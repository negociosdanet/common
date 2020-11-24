package com.negociosdanet.common.domain.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.negociosdanet.common.domain.enumerate.SituationEnum;

import lombok.Data;

@Data
public class StoreDto {

	@NotNull(message = "storeId is mandatory")
	private Long storeId;
	private String name;
	private String cpfOrCnpj;
	private String urlFacebook;
	private String urlYouTube;
	private String urlInstagram;
	private SituationEnum status;

	private List<CategoryDto> categories;

	@JsonIgnore
	private UserDto user;
	private AddressDto address;

	public boolean isActive() {
		return SituationEnum.ACTIVE.equals(this.status);
	}
}
