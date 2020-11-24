package com.negociosdanet.common.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.negociosdanet.common.domain.enumerate.UserRole;
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
	
	private static final String USER_AUTHENTICATE = "userAuthenticate";

	public String createTokenAdmin(Authentication authentication) {
		UserAuthenticate userAuthenticate = getUserAutenticate(authentication);
		Date now = new Date();
		String jti = UUID.randomUUID().toString();

		Map<String, Object> claims = new HashMap<>();
		claims.put(USER_AUTHENTICATE, userAuthenticate);
		claims.put(UserRole.ADMIN.name(), true);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setId(jti)
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
			throw e;
		}
	}

	public UserAuthenticate getUserToken(String token) {
		if (token != null) {
			token = token.substring(7, token.length());
		}
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return JsonUtils.convertValue(claims.get(USER_AUTHENTICATE), UserAuthenticate.class);
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

	public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = recuperarToken(request);
		
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			UserAuthenticate authenticate = JsonUtils.convertValue(claims.get(USER_AUTHENTICATE), UserAuthenticate.class);
			
			return new UsernamePasswordAuthenticationToken(authenticate.getName(), null, getAuthorities(claims));
		}
		
		return null;
	}
	
	public List<GrantedAuthority> getAuthorities(Claims claims) {
		if (Boolean.TRUE.equals(claims.get(UserRole.SHOP_OWNER.name()))) {
			return Collections.singletonList(UserRole.SHOP_OWNER.getAuthority());
		} else if (Boolean.TRUE.equals(claims.get(UserRole.CUSTOMER.name()))) {
			return Collections.singletonList(UserRole.CUSTOMER.getAuthority());
		}
		
		return Collections.singletonList(UserRole.ADMIN.getAuthority());
	}

}
