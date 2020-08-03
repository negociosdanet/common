package com.negociosdanet.common.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.negociosdanet.common.domain.response.UserAuthenticate;
import com.negociosdanet.common.utils.JsonUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${auth.jwt.expiration}")
	private String expiration;

	@Value("${auth.jwt.secret}")
	private String secret;

	private static final String BEARER = "Bearer ";

	public String createToken(Authentication authentication) {
		UserAuthenticate logado = getUserAutenticate(authentication);
		Date now = new Date();

		return Jwts.builder().setSubject(JsonUtils.convertToJson(logado)).setIssuedAt(now)
				.setExpiration(getExpirationDate(now)).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	private Date getExpirationDate(Date hoje) {
		return new Date(hoje.getTime() + Long.parseLong(expiration));
	}

	public boolean isTokenValid(String token) {

		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		UserAuthenticate authenticate = JsonUtils.convertJsonToObj(claims.getSubject(), UserAuthenticate.class);
		return authenticate.getId();
	}

	public UserAuthenticate getUserToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return JsonUtils.convertJsonToObj(claims.getSubject(), UserAuthenticate.class);
	}

	private UserAuthenticate getUserAutenticate(Authentication authentication) {
		return JsonUtils.convertValue(authentication.getPrincipal(), UserAuthenticate.class);
	}

	public String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token == null || token.isEmpty() || !token.startsWith(BEARER)) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
