/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.service.TestRestServicesApplication;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRestServicesApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = {"com.greek.main.hibernate.model"})
@DataJpaTest
public class GeographicLocationJPALayerIT {

    @Autowired private GeographicLocationRepository geographicLocationRepository;

    @Test
    public void when_find_all_provinces_by_spain_ok() {
        UbicacionGeografica ubicacionGeografica =
                geographicLocationRepository.findByCodigoUbicacionGeografica("ES").get();

        assertThat(ubicacionGeografica.getNombreUbicacionGeografica(), is("España"));
    }

    @Test
    public void when_find_all_countries_ok() {
        List<UbicacionGeografica> countries =
                geographicLocationRepository.findByUbicacionGeograficaIsNull();

        assertThat(countries.size(), is(26));
    }
}
