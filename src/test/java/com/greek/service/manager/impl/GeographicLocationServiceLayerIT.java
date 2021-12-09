/* AssentSoftware (C)2021 */
package com.greek.service.manager.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.service.TestRestServicesApplication;
import com.greek.service.manager.SimpleDomainService;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestRestServicesApplication.class})
@TestPropertySource({"classpath:application.properties"})
@Import(value = {OAuth2ResourceServerProperties.class})
@Slf4j
public class GeographicLocationServiceLayerIT {

    @Autowired private SimpleDomainService simpleDomainService;
    
	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.FRENCH);
	}

    @Test
    public void getCountryNames() {
        List<UbicacionGeografica> countries = simpleDomainService.findAllGeographicLocations();

        for (UbicacionGeografica country : countries) {
            log.debug(
                    "Country code:{} country name:{}",
                    country.getCodigoUbicacionGeografica(),
                    country.getNombreUbicacionGeografica());
        }

        assertThat(countries.size(), is(26));
    }
}
