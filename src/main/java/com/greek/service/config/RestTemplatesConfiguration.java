/* AssentSoftware (C)2021 */
package com.greek.service.config;

import com.gvt.rest.http.client.LocaleHeaderInterceptor;
import com.gvt.rest.web.client.CustomResponseErrorHandler;
import com.gvt.security.http.client.AuthorizationHeaderInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Configuration
public class RestTemplatesConfiguration {

    @Bean
    public RestTemplate restTemplate(
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
            ClientHttpRequestFactory clientHttpRequestFactory,
            Logbook logbook) {
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //		messageConverters.add(new FormHttpMessageConverter());
        //		messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(mappingJackson2HttpMessageConverter);

        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(
                new CustomResponseErrorHandler(
                        mappingJackson2HttpMessageConverter.getObjectMapper()));
        restTemplate.setMessageConverters(messageConverters);
        restTemplate.getInterceptors().add(new LocaleHeaderInterceptor());
        restTemplate.getInterceptors().add(new AuthorizationHeaderInterceptor());
        restTemplate.getInterceptors().add(new LogbookClientHttpRequestInterceptor(logbook));
        //		restTemplate.getInterceptors().add(new LoggerInterceptor());

        return restTemplate;
    }
}
