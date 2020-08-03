package com.negociosdanet.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.negociosdanet.common.security.brute_force.BruteForceFilter;

@Configuration
@ComponentScan("com.negociosdanet.common")
public class CommonConfig {
	
	@Value("${auth.security.brute_force.timeRequest}")
	private long timeRequest;
	
	@Value("${auth.security.brute_force.numberAttempt}")
	private int numberAttempt;
	
	@Bean
	public FilterRegistrationBean<BruteForceFilter> someFilterRegistration(BruteForceFilter filterBrute) {
	    FilterRegistrationBean<BruteForceFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(filterBrute);
	    registration.addUrlPatterns("/login");
	    registration.setName(BruteForceFilter.class.getSimpleName());
	    registration.setOrder(1);
	    return registration;
	}

}
