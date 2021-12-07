package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.greek.main.hibernate.model.CodigoPostal;
import com.gvt.data.JPAConfiguration;
import com.gvt.data.security.support.SecurityEvaluationContextExtension;
import com.gvt.security.test.context.support.WithMockedUser;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { JPAConfiguration.class, DataSourceAutoConfiguration.class,
		SecurityEvaluationContextExtension.class })
@EnableJpaRepositories(basePackages = { "com.greek.service.repositories" })
@TestPropertySource({ "classpath:application.properties" })
@WithMockedUser
@Slf4j
public class PostalCodesJPALayerIT {

	@Autowired
	private PostalCodesRepository postalCodesRepository;

	@Test
	public void when_find_all_postal_codes_by_province_and_municipality() {
		List<CodigoPostal> postalCodes = postalCodesRepository.findAll(1L, 40l);

		for (CodigoPostal cp : postalCodes) {
			log.debug("Codigo postal:{}", cp.getCodigoPostal());
		}

		assertThat(postalCodes.size(), is(2));
	}

}