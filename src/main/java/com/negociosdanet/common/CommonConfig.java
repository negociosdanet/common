package com.negociosdanet.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.negociosdanet.common.security.bruteforce.BruteForceFilter;

@Configuration
@ComponentScan("com.negociosdanet.common")
public class CommonConfig {
	
	@Value("${auth.security.bruteforce.timeRequest}")
	private long timeRequest;
	
	@Value("${auth.security.bruteforce.numberAttempt}")
	private int numberAttempt;
	
	@Bean
	public FilterRegistrationBean<BruteForceFilter> bruteForceFilter(BruteForceFilter filterBrute) {
	    FilterRegistrationBean<BruteForceFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(filterBrute);
	    registration.addUrlPatterns("/login");
	    registration.setName(BruteForceFilter.class.getSimpleName());
	    registration.setOrder(1);
	    return registration;
	}

}
