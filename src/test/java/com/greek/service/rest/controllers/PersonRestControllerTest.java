/* AssentSoftware (C)2021 */
package com.greek.service.rest.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.greek.service.TestRestServicesApplication;
import com.greek.service.exceptions.PersonAlreadyExistsDifferentOrganizationException;
import com.greek.service.exceptions.PersonAlreadyExistsSameOrganizationException;
import com.greek.service.manager.PersonService;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.PersonMapperImpl_;
import com.greek.service.utils.ObjectsBuilderUtils;
import com.gvt.rest.RestServicesConfiguration;
import com.gvt.security.SecurityOAuth2Configuration;
import com.gvt.security.test.context.support.WithMockedUser;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {PersonRestController.class})
@ContextConfiguration(
        classes = {
            TestRestServicesApplication.class,
            SecurityOAuth2Configuration.class,
            RestServicesConfiguration.class,
            PersonMapperImpl_.class
        })
@WithMockedUser
public class PersonRestControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private PersonService personService;

    @MockBean private SimpleDomainService simpleDomainService;

    private Faker faker = new Faker(new Locale("es", "ES"));

    @Test
    public void when_person_exists_same_organization() throws Exception {
        doThrow(new DataIntegrityViolationException(null)).when(personService).save(any());
        doThrow(new PersonAlreadyExistsSameOrganizationException(null))
                .when(personService)
                .checkPersonIsPresentBehaviour(any(), any());

        mockMvc.perform(
                        post("/api/v1/persons")
                                .content(
                                        objectMapper.writeValueAsString(
                                                ObjectsBuilderUtils.createFullPersonDto(faker)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(
                        jsonPath(
                                "$.message_key",
                                is("error.person.already.exists.same.organization")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void when_person_exists_different_organization() throws Exception {
        doThrow(new DataIntegrityViolationException(null)).when(personService).save(any());
        doThrow(new PersonAlreadyExistsDifferentOrganizationException(null))
                .when(personService)
                .checkPersonIsPresentBehaviour(any(), any());

        mockMvc.perform(
                        post("/api/v1/persons")
                                .content(
                                        objectMapper.writeValueAsString(
                                                ObjectsBuilderUtils.createFullPersonDto(faker)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(
                        jsonPath(
                                "$.message_key",
                                is("error.person.already.exists.different.organization")))
                .andExpect(status().is4xxClientError());
    }
}
