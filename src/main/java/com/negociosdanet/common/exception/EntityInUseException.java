package com.negociosdanet.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityInUseException extends RuntimeException {

	private static final long serialVersionUID = 591168349341932803L;

	public static final String DEFAULT_MESSAGE_ENTITY_IN_USE = "%s de código %d não pode ser removida, pois está em uso";

	public EntityInUseException(String message) {
		super(message);
	}

	public EntityInUseException(String entity, Long code) {
		super(String.format(DEFAULT_MESSAGE_ENTITY_IN_USE, entity, code));
	}

}
