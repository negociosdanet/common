package com.negociosdanet.common.domain.dto;

import lombok.Data;

@Data
public class AddressDto {

	private String cep;
	private String logradouro;
	private String number;
	private String complement;
	private String district;
	private String state;
	private String city;

}
