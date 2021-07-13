package com.greek.service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.zalando.logbook.CorrelationId;

import com.greek.service.filters.CorrelationMDCInjectionFilter;

@Configuration
public class CorrelationLoggingConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public FilterRegistrationBean<CorrelationMDCInjectionFilter> correlationIdMDCInjectionFilterRegistration(
			CorrelationMDCInjectionFilter correlationIdMDCInjectionFilter) {
		FilterRegistrationBean<CorrelationMDCInjectionFilter> contextFilter = new FilterRegistrationBean<>();
		contextFilter.setFilter(correlationIdMDCInjectionFilter);
		contextFilter.addUrlPatterns("/*");

		return contextFilter;
	}

	@Bean
	public CorrelationMDCInjectionFilter correlationIdMDCInjectionFilter(CorrelationId correlationId) {
		return new CorrelationMDCInjectionFilter(correlationId); // now this is a Spring bean
	}

}
