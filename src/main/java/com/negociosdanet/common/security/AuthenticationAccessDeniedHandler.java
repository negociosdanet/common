package com.negociosdanet.common.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.negociosdanet.common.domain.response.ApiResponse;
import com.negociosdanet.common.utils.JsonUtils;

public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		apiResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		PrintWriter writer = response.getWriter();
		writer.println(JsonUtils.convertToJson(apiResponse));
	}

}
