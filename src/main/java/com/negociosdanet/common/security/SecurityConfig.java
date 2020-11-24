package com.negociosdanet.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.negociosdanet.common.filter.AuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationFilter authenticationFilter;
	
	@Autowired
	private UrlAuthorizationRegistry urlAuthorizationRegistry;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = 
				http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.enableSessionUrlRewriting(false)
				.and()
				.csrf()
				.disable()
				.cors()
				.and()
				.logout()
				.disable()
				.exceptionHandling()
				.accessDeniedHandler(authenticationAccessDeniedHandler())
				.authenticationEntryPoint(authenticationEntryPoint())
				.and()
				.authorizeRequests();
		
		urlAuthorizationRegistry.configureRegistry(registry);
		
		registry.antMatchers("/**")
			.authenticated()
			.and()
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler() {
		return new AuthenticationAccessDeniedHandler();
	}
	
	@Bean
	public AuthenticationSecurityEntryPoint authenticationEntryPoint() {
		return new AuthenticationSecurityEntryPoint();
	}

}
