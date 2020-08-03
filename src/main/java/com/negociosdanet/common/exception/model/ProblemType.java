package com.negociosdanet.common.exception.model;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	BUSINESS_EXCEPTION("/erro-generico", "Violação das regras de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	UNSPECIFIED_ERROR("/", "Erro generico, analise os logs para mais detalhes");

	private String title;
	private String uri;
	private static final String HOST = "https://negogiosdanet.com.br%s";

	ProblemType(String path, String title) {
		this.uri = String.format(HOST, path);
		this.title = title;
	}

}