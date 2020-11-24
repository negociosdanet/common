package com.negociosdanet.common.domain.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UserAuthenticate {

	private Long id;
	private String name;
	private String email;
	private RoleResponse role;
	private PlanResponse plan;
	private Collection<? extends GrantedAuthority> authoritys;

}
