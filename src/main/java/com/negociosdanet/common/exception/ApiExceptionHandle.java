package com.negociosdanet.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.negociosdanet.common.exception.model.ProblemType;
import com.negociosdanet.common.exception.model.Wrapper;

@RestControllerAdvice
public class ApiExceptionHandle extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		String detail = "O corpo da requsição está inválido. Verifique erros de sintaxe.";
		Wrapper wrapper = createWrapper(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail, request).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Wrapper wrapper = createWrapper(status, problemType, detail, request).build();

		return this.handleExceptionInternal(ex, wrapper, headers, status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBadRequestException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Wrapper wrapper = createWrapper(status, ProblemType.BUSINESS_EXCEPTION, ex.getMessage(), request).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(CredentialsException.class)
	public ResponseEntity<Object> handleCredentialsNotFoundException(CredentialsException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Wrapper wrapper = createAlternativeWrapper(ex.getMessage(), status.value(), null, request);
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Wrapper wrapper = createWrapper(status, ProblemType.ENTIDADE_NAO_ENONTRADA, ex.getMessage(), request).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<Object> handleEntidadeEmUsoException(EntityInUseException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Wrapper wrapper = createWrapper(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage(), request).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleNullPointer(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Wrapper wrapper = createWrapper(status, ProblemType.UNSPECIFIED_ERROR, ex.getMessage(), request).build();
		return this.handleExceptionInternal(ex, wrapper, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = createAlternativeWrapper(status.getReasonPhrase(), status.value(), ex.getMessage(), request);
		} else if (body instanceof String) {
			body = createAlternativeWrapper((String) body, status.value(), null, request);
		}
		
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Wrapper.WrapperBuilder createWrapper(HttpStatus status, ProblemType problemType, String detail,
			WebRequest request) {
		return Wrapper.builder().date(LocalDateTime.now()).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).detail(detail)
				.path(((ServletWebRequest) request).getRequest().getRequestURI());
	}

	private Wrapper createAlternativeWrapper(String title, int status, String detail, WebRequest request) {
		return Wrapper.builder().date(LocalDateTime.now()).title(title).status(status).detail(detail)
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();
	}

}