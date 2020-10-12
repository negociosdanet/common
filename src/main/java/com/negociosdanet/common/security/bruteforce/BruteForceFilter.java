package com.negociosdanet.common.security.bruteforce;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.negociosdanet.common.exception.BusinessException;

@Component
public class BruteForceFilter implements Filter {

	public static final String CLASS = BruteForceFilter.class.getSimpleName();
	
	@Autowired
	private BruteForceService service;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		service.registerAccess(request);
		if (!service.canLogin()) {
			throw new BusinessException("Seu IP está temporariamente bloqueado.");
		}
		
		chain.doFilter(request, response);
	}

}
