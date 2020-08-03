package com.negociosdanet.common.security.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.negociosdanet.common.domain.response.UserAuthenticate;
import com.negociosdanet.common.service.TokenService;
import com.negociosdanet.service_client.UserClient;
import com.negociosdanet.service_client.response.UserDetails;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Value("${auth.jwt.expiration}")
	private String expiration;

	@Value("${auth.jwt.secret}")
	private String secret;

	@Autowired
	private UserClient userClient;

	@Autowired
	private TokenService tokenService;
	
	private static final String LOGIN_PAGE = "/login";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		boolean isLogin = LOGIN_PAGE.equals(request.getRequestURI());

		if (!isLogin) {
			String token = tokenService.recuperarToken(request);
			if (tokenService.isTokenValid(token)) {
				autenticarUser(token);
			}
		}

		filterChain.doFilter(request, response);
	}

	private void autenticarUser(String token) {
		Optional<UserDetails> usuarioOptional = Optional
				.ofNullable(userClient.findUserById(tokenService.getIdUser(token)));

		if (usuarioOptional.isPresent()) {
			UserAuthenticate userAuthenticate = tokenService.getUserToken(token);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userAuthenticate.getName(), null, null);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

	}

}
