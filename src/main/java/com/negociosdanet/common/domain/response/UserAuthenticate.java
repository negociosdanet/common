package com.negociosdanet.common.domain.response;

import lombok.Data;

@Data
public class UserAuthenticate {

	private Long id;
	private String name;
	private String email;
	private RoleResponse role;

}
