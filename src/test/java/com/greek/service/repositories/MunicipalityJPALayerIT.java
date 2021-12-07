/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.greek.main.hibernate.model.Poblacion;
import com.gvt.data.JPAConfiguration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JPAConfiguration.class, DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {"com.greek.service.repositories"})
@TestPropertySource({"classpath:application.properties"})
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
