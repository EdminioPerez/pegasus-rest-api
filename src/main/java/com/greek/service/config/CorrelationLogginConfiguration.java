package com.greek.service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.zalando.logbook.CorrelationId;

import com.greek.service.filters.CorrelationIdMDCInjectionFilter;

@Configuration
public class CorrelationLogginConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public FilterRegistrationBean<CorrelationIdMDCInjectionFilter> correlationIdMDCInjectionFilterRegistration(
			CorrelationIdMDCInjectionFilter correlationIdMDCInjectionFilter) {
		FilterRegistrationBean<CorrelationIdMDCInjectionFilter> contextFilter = new FilterRegistrationBean<>();
		contextFilter.setFilter(correlationIdMDCInjectionFilter);
		contextFilter.addUrlPatterns("/*");

		return contextFilter;
	}

	@Bean
	public CorrelationIdMDCInjectionFilter correlationIdMDCInjectionFilter(CorrelationId correlationId) {
		return new CorrelationIdMDCInjectionFilter(correlationId); // now this is a Spring bean
	}

}
