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

import com.greek.main.hibernate.model.Provincia;
import com.gvt.data.JPAConfiguration;
import com.gvt.data.security.support.SecurityEvaluationContextExtension;
import com.gvt.security.test.context.support.WithMockedUser;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { JPAConfiguration.class, DataSourceAutoConfiguration.class,
		SecurityEvaluationContextExtension.class })
@EnableJpaRepositories(basePackages = { "com.greek.service.repositories" })
@TestPropertySource({ "classpath:application.properties" })
@WithMockedUser
public class ProvinceJPALayerIT {

	@Autowired
	private ProvinceRepository provinceRepository;

	@Test
	public void when_find_all_provinces_by_spain_ok() {
		List<Provincia> provinces = provinceRepository.findProvinces();

		assertThat(provinces.size(), is(52));
	}

}