package com.negociosdanet.common.domain.enumerate;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("Admin"), SHOP_OWNER("ShopOwner"), CUSTOMER("Customer");

	private String role;
	private SimpleGrantedAuthority authority;

	UserRole(String role) {
		this.role = role;
		this.authority = new SimpleGrantedAuthority(this.name());
	}

}
