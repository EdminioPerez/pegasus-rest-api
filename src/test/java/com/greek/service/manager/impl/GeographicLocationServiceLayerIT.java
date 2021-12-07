package com.greek.service.manager.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.service.manager.SimpleDomainService;
import com.gvt.data.JPAConfiguration;
import com.gvt.rest.RestServicesConfiguration;
import com.gvt.rest.context.i18n.Translator;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { JPAConfiguration.class, DataSourceAutoConfiguration.class, Translator.class,
		RestServicesConfiguration.class, SimpleDomainServiceImpl.class })
@EnableJpaRepositories(basePackages = { "com.greek.service.repositories" })
@TestPropertySource({ "classpath:application.properties" })
@Slf4j
public class GeographicLocationServiceLayerIT {

	@BeforeEach
	public static void setDefaultLocale() {
		Locale.setDefault(Locale.FRENCH);
	}

	@Autowired
	private SimpleDomainService simpleDomainService;

	@Test
	public void getCountryNames() {
		List<UbicacionGeografica> countries = simpleDomainService.findAllGeographicLocations();

		for (UbicacionGeografica country : countries) {
			log.debug("Country code:{} country name:{}", country.getCodigoUbicacionGeografica(),
					country.getNombreUbicacionGeografica());
		}

		assertThat(countries.size(), is(26));
	}

}
