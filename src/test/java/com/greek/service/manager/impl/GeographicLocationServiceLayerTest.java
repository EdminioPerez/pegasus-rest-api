/* AssentSoftware (C)2021 */
package com.greek.service.manager.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.repositories.GeographicLocationRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
public class GeographicLocationServiceLayerTest {

    @Mock private GeographicLocationRepository geographicLocationRepository;

    @Mock private MessageSource messageSource;

    private SimpleDomainService simpleDomainService;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.FRENCH);

        simpleDomainService =
                new SimpleDomainServiceImpl(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        geographicLocationRepository,
                        messageSource);
    }

    @Test
    public void getCountryNames() {
        when(geographicLocationRepository.findByUbicacionGeograficaIsNull())
                .thenReturn(
                        Arrays.asList(
                                new UbicacionGeografica("001", "Venezuela", "VE"),
                                new UbicacionGeografica("002", "España", "ES")));
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Country translation");

        List<UbicacionGeografica> countries = simpleDomainService.findAllGeographicLocations();

        for (UbicacionGeografica country : countries) {
            log.debug(
                    "Country code:{} country name:{}",
                    country.getCodigoUbicacionGeografica(),
                    country.getNombreUbicacionGeografica());
        }

        assertThat(countries.size(), is(2));
    }
}
