package com.negociosdanet.common.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@FunctionalInterface
public interface UrlAuthorizationRegistry {
	
	void addToRegistry(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry);

	default void configureRegistry(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		addToRegistry(registry);
		addActuatorDefaults(registry);
		addBatchDefaults(registry);
		addSwaggerDefaults(registry);
		addCacheDefaults(registry);
		addApplicationDefaults(registry);
	}

	default void addSwaggerDefaults(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		// Swagger
        registry.antMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/**/api-docs")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/configuration/ui")
                .permitAll();
	}

	default void addBatchDefaults(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers(HttpMethod.GET, "/batch/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/batch/**")
        .permitAll();
	}

	default void addActuatorDefaults(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers(HttpMethod.GET, "/actuator/**").permitAll();
	}

	default void addCacheDefaults(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers(HttpMethod.DELETE, "/cache");
	}

	default void addApplicationDefaults(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers(HttpMethod.GET, "/**").hasAnyAuthority()
				.antMatchers(HttpMethod.POST, "/**").hasAnyAuthority()
				.antMatchers(HttpMethod.PUT, "/**").hasAnyAuthority()
				.antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority()
				.antMatchers(HttpMethod.PATCH).denyAll();
	}
}