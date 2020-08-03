package com.negociosdanet.common.exception;

public class EntityNotFoundException extends NotFoundException {

	public static final String DEFAULT_MESSAGE_NOT_FOUND = "Não existe um cadastro de %s com código %d.";

	private static final long serialVersionUID = 1738659866084102867L;

	public EntityNotFoundException() {
		super("Registo não encontrado");
	}

	public EntityNotFoundException(String mensagem) {
		super(mensagem);
	}

	public EntityNotFoundException(String entity, Long code) {
		super(String.format(DEFAULT_MESSAGE_NOT_FOUND, entity, code));
	}

}
