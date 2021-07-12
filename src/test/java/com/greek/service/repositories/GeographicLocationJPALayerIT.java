package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.greek.main.hibernate.model.UbicacionGeografica;
import com.gvt.data.JPAConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, DataSourceAutoConfiguration.class })
@EnableJpaRepositories(basePackages = { "com.greek.service.repositories" })
@TestPropertySource({ "classpath:application.properties" })
public class GeographicLocationJPALayerIT {

	@Autowired
	private GeographicLocationRepository geographicLocationRepository;

	@Test
	public void when_find_all_provinces_by_spain_ok() {
		UbicacionGeografica ubicacionGeografica = geographicLocationRepository.findByCodigoUbicacionGeografica("ES")
				.get();

		assertThat(ubicacionGeografica.getNombreUbicacionGeografica(), is("España"));
	}

	@Test
	public void when_find_all_countries_ok() {
		List<UbicacionGeografica> countries = geographicLocationRepository.findByUbicacionGeograficaIsNull();

		assertThat(countries.size(), is(26));
	}

}