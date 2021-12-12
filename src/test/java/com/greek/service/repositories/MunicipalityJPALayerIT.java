/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.greek.main.hibernate.model.Poblacion;
import com.greek.service.TestRestServicesApplication;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MunicipalityJPALayerIT {

    @Autowired private MunicipalityRepository municipalityRepository;

    @Test
    public void when_find_all_municipalities_by_province() {
        List<Poblacion> municipalities = municipalityRepository.findMunicipalities(1l);

        for (Poblacion poblacion : municipalities) {
            log.debug("Municipality:{}", poblacion.getNombrePoblacion());
        }

        assertThat(municipalities.size(), is(51));
    }
}
