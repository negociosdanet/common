package com.negociosdanet.common.domain.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 6980900658259709838L;
	private final String jwtToken;
}
