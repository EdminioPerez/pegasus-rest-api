package com.greek.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.gvt.rest.http.client.LocaleHeaderInterceptor;
import com.gvt.rest.web.client.DefaultResponseErrorHandler;
import com.gvt.security.http.client.AuthorizationHeaderInterceptor;

@Configuration
public class RestTemplatesConfiguration {

	@Bean
	public RestTemplate restTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
			ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(mappingJackson2HttpMessageConverter);

		restTemplate.setRequestFactory(clientHttpRequestFactory);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.getInterceptors().add(new LocaleHeaderInterceptor());
		restTemplate.getInterceptors().add(new AuthorizationHeaderInterceptor());

		return restTemplate;
	}

}
