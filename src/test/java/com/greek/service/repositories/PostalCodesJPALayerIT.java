/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.service.TestRestServicesApplication;
import com.greek.service.security.jwt.converters.CustomClaimsConverter;
import com.gvt.security.SecurityOAuth2Configuration;
import com.gvt.security.test.context.support.WithMockedUser;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
            TestRestServicesApplication.class,
            SecurityOAuth2Configuration.class,
            OAuth2ResourceServerProperties.class,
            CustomClaimsConverter.class
        })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = {"com.greek.main.hibernate.model"})
// @Import(value = { })
@DataJpaTest
@WithMockedUser
@Slf4j
public class PostalCodesJPALayerIT {

    @Autowired private PostalCodesRepository postalCodesRepository;

    @Test
    public void when_find_all_postal_codes_by_province_and_municipality() {
        List<CodigoPostal> postalCodes = postalCodesRepository.findAll(1L, 40l);

        for (CodigoPostal cp : postalCodes) {
            log.debug("Codigo postal:{}", cp.getCodigoPostal());
        }

        assertThat(postalCodes.size(), is(2));
    }
}
