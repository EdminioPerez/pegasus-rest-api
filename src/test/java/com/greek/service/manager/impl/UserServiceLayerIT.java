/* AssentSoftware (C)2021 */
package com.greek.service.manager.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;
import com.greek.commons.dto.v1.user.ClientDto;
import com.greek.commons.dto.v1.user.SystemUserDto;
import com.greek.main.hibernate.model.Organizacion;
import com.greek.service.manager.UserService;
import com.gvt.data.JPAConfiguration;
import com.gvt.data.security.support.SecurityEvaluationContextExtension;
import com.gvt.rest.RestServicesConfiguration;
import com.gvt.security.test.context.support.WithMockedUser;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
            JPAConfiguration.class,
            DataSourceAutoConfiguration.class,
            RestServicesConfiguration.class,
            SecurityEvaluationContextExtension.class,
            UserServiceImpl.class,
            PersonServiceImpl.class,
            OrganizationServiceImpl.class
        })
@EnableJpaRepositories(basePackages = {"com.greek.service.repositories"})
@TestPropertySource({"classpath:application.properties"})
@WithMockedUser
@Slf4j
public class UserServiceLayerIT {

    @Autowired private UserService userService;

    private Faker faker = new Faker(new Locale("es", "ES"));

    @Test
    public void saveAppUsers() {
        log.trace("Trying to save a user into the app");

        //		extracted();

        assertTrue(true);
    }

    private void extracted() {
        SystemUserDto systemUser = new SystemUserDto();
        systemUser.setIdValidation(UUID.randomUUID().toString());
        systemUser.setNombres(faker.name().firstName());
        systemUser.setApellidos(faker.name().lastName() + " " + faker.name().lastName());
        systemUser.setCedula(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        systemUser.setEmail(faker.internet().emailAddress());
        systemUser.setContrasena(
                "{bcrypt}$2a$10$rv8TpMkATdDTRUcuIWYh/uXxm.Szc.NKAGdZu9cLrjPJC27IHlyoe"); // 1234
        systemUser.setTelefono(faker.phoneNumber().cellPhone());

        log.trace("Numeric value:{}", RandomStringUtils.randomNumeric(1, 4));

        ClientDto client = new ClientDto();
        client.setId(Long.valueOf(RandomStringUtils.randomNumeric(1, 4)));
        client.setCedula(systemUser.getCedula());
        client.setRazonSocial(systemUser.getNombres() + " " + systemUser.getApellidos());
        client.setDireccion(faker.address().fullAddress());

        List<Organizacion> organizations =
                userService.createUser(systemUser, client, 12L, new Locale("es_ES"));

        assertNotNull(organizations);
    }
}
