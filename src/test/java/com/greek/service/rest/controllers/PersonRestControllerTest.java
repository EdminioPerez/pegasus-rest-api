package com.greek.service.rest.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.javafaker.Faker;
import com.gvt.security.SecurityConstants;
import com.gvt.security.SecurityOAuth2Configuration;
import com.gvt.security.test.context.support.WithMockedUser;
import com.gvt.security.test.utils.JwtTestUtils;
import com.greek.service.TestRestServicesApplication;
import com.greek.service.exceptions.PersonAlreadyExistsDifferentOrganizationException;
import com.greek.service.exceptions.PersonAlreadyExistsSameOrganizationException;
import com.greek.service.manager.PersonService;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.PersonMapperImpl_;
import com.greek.service.utils.ObjectsBuilderUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { PersonRestController.class })
@ContextConfiguration(classes = { TestRestServicesApplication.class, SecurityOAuth2Configuration.class })
@TestPropertySource({ "classpath:application.properties" })
@Import({ PersonMapperImpl_.class })
@WithMockedUser
public class PersonRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

	@MockBean
	private PersonService personService;

	@MockBean
	private SimpleDomainService simpleDomainService;

	private Faker faker = new Faker(new Locale("es", "ES"));

	@Test
	public void when_person_exists_same_organization() throws Exception {
		doThrow(new DataIntegrityViolationException(null)).when(personService).save(any());
		doThrow(new PersonAlreadyExistsSameOrganizationException(null)).when(personService)
				.checkPersonIsPresentBehaviour(any(), any());

		mockMvc.perform(post("/api/v1/persons")
				.content(mappingJackson2HttpMessageConverter.getObjectMapper()
						.writeValueAsString(ObjectsBuilderUtils.createFullPersonDTO(faker)))
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.header(SecurityConstants.HEADER_AUTHORIZATION, JwtTestUtils.builJwtBearerFromMockUser()))
				.andDo(print()).andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.message_key", is("error.person.already.exists.same.organization")))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void when_person_exists_different_organization() throws Exception {
		doThrow(new DataIntegrityViolationException(null)).when(personService).save(any());
		doThrow(new PersonAlreadyExistsDifferentOrganizationException(null)).when(personService)
				.checkPersonIsPresentBehaviour(any(), any());

		mockMvc.perform(post("/api/v1/persons")
				.content(mappingJackson2HttpMessageConverter.getObjectMapper()
						.writeValueAsString(ObjectsBuilderUtils.createFullPersonDTO(faker)))
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.header(SecurityConstants.HEADER_AUTHORIZATION, JwtTestUtils.builJwtBearerFromMockUser()))
				.andDo(print()).andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.message_key", is("error.person.already.exists.different.organization")))
				.andExpect(status().is4xxClientError());
	}

}