package com.greek.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.gvt.security.config.DefaultJwtSecurityConfiguration;
import com.gvt.security.oauth2.secret.DefaultJwtSecretKeyFactory;
import com.gvt.security.oauth2.secret.JwtSecretKeyFactory;

@Configuration
public class JwtSecurityConfiguration extends DefaultJwtSecurityConfiguration {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);

		http.authorizeRequests().anyRequest().authenticated();
	}

	@Bean
	public JwtSecretKeyFactory jwtSecretKeyFactory(@Value("${app.jwt.secretKey}") String secretKey) {
		return new DefaultJwtSecretKeyFactory(secretKey);
	}

}