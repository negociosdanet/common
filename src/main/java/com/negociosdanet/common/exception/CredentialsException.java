package com.negociosdanet.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CredentialsException(String mensagem) {
		super(mensagem);
	}

	public CredentialsException() {
		super("Usuário ou senha não encontrado! Por favor tente novamente.");
	}

}
