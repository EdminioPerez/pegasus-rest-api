package com.greek.service.rest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.ValidationException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.javafaker.Faker;
import com.greek.commons.dto.v1.person.PersonDTO;
import com.greek.service.RestServicesApplication;
import com.greek.service.utils.ObjectsBuilderUtils;
import com.gvt.core.exceptions.DataIntegrityException;
import com.gvt.core.reflect.ReflectionUtils;
import com.gvt.security.SecurityConstants;
import com.gvt.security.test.context.support.WithMockedUser;
import com.gvt.security.test.utils.JwtTestUtils;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RestServicesApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@WithMockedUser(centersIds = { 1, 2, 3 }, rootCenterId = 1)
@Slf4j
public class PersonRestControllerIT {

	private static final String PREFIX = "/api/v1/persons";

	@LocalServerPort
	private int serverPort;

	@Autowired
	private RestTemplate restTemplate;

	private Faker faker = new Faker(new Locale("es", "ES"));
	private UriComponentsBuilder uriComponents;
	private HttpEntity<String> requestEntity;

	@Before
	public void setUp() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(SecurityConstants.HEADER_AUTHORIZATION, JwtTestUtils.builJwtBearerFromMockUser());
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		uriComponents = buildUriComponent();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestEntity = new HttpEntity<>(requestHeaders);
	}

//	@Test
	public void save_full_person() {
		// The assertions are in the saveFullPerson
		log.debug("************************");
		this.saveFullPerson();
	}

//	@Test
	public void save_minimal_person() {
		// The assertions are in the saveMinimalPerson
		log.debug("************************");
		this.saveMinimalPerson();
	}

	@Test
	public void save_full_person_already_exists() {
		log.debug("************************");
		PersonDTO firstSave = this.saveFullPerson();

		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(firstSave), PersonDTO.class);

			assertTrue(false);
		} catch (HttpClientErrorException e) {
			log.debug("Body of the error:{}", e.getResponseBodyAsString());
			log.debug("Exception:{}", e);
			assertEquals("error.person.already.exists.same.organization", e.getMessage());
		}
	}

//	@Test
	public void patch_person_ok() throws Exception {
		PersonDTO personDTO = saveFullPerson();

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", personDTO.getId().toString());

		log.debug("*************************************************************************");

		PersonDTO personPatchDTO = new PersonDTO();
		personPatchDTO.setId(-123L);
		personPatchDTO.setCode("no cambiar");
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setName(faker.name().firstName());
		personDTO.setName(personPatchDTO.getName());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setLastName(faker.name().lastName() + " " + faker.name().lastName());
		personDTO.setLastName(personPatchDTO.getLastName());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setIdentityDocumentTypeId(2L);
		personDTO.setIdentityDocumentTypeId(personPatchDTO.getIdentityDocumentTypeId());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setIdentityDocument(
				RandomStringUtils.randomAlphanumeric(1) + "-" + RandomStringUtils.randomNumeric(14));
		personDTO.setIdentityDocument(personPatchDTO.getIdentityDocument());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSanitaryDocument(
				RandomStringUtils.randomAlphanumeric(1) + "-" + RandomStringUtils.randomNumeric(14));
		personDTO.setSanitaryDocument(personPatchDTO.getSanitaryDocument());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setMobilePhone(faker.phoneNumber().cellPhone());
		personDTO.setMobilePhone(personPatchDTO.getMobilePhone());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setHomePhone(faker.phoneNumber().phoneNumber());
		personDTO.setHomePhone(personPatchDTO.getHomePhone());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setEmail(faker.internet().emailAddress());
		personDTO.setEmail(personPatchDTO.getEmail());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setAddressLine1(faker.address().fullAddress());
		personPatchDTO.setAddressLine2(faker.address().secondaryAddress());
		personDTO.setAddressLine1(personPatchDTO.getAddressLine1());
		personDTO.setAddressLine2(personPatchDTO.getAddressLine2());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setProvinceId(1L);
		personPatchDTO.setMunicipalityId(44L);
		personPatchDTO.setPostalCodeId(100L);
		personDTO.setProvinceId(personPatchDTO.getProvinceId());
		personDTO.setMunicipalityId(personPatchDTO.getMunicipalityId());
		personDTO.setPostalCodeId(personPatchDTO.getPostalCodeId());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		LocalDate birthDate = new java.sql.Date(faker.date().birthday().getTime()).toLocalDate();
		personPatchDTO.setBirthDate(birthDate);
		personDTO.setBirthDate(personPatchDTO.getBirthDate());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setAge((float) Period.between(birthDate, LocalDate.now()).getYears());
		personDTO.setAge(personPatchDTO.getAge());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setUrl1FileName(faker.file().fileName());
		personDTO.setUrl1FileName(personPatchDTO.getUrl1FileName());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSexId(faker.random().nextInt(1, 2).longValue());
		personPatchDTO.setBloodGroupId(faker.random().nextInt(1, 8).longValue());
		personPatchDTO.setCountryBirthId(faker.random().nextInt(1, 14).longValue());
		personDTO.setSexId(personPatchDTO.getSexId());
		personDTO.setBloodGroupId(personPatchDTO.getBloodGroupId());
		personDTO.setCountryBirthId(personPatchDTO.getCountryBirthId());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);
	}

//	@Test
	public void patch_person_nullify_ok() throws Exception {
		PersonDTO personDTO = saveFullPerson();

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", personDTO.getId().toString());

		log.debug("*************************************************************************");

		PersonDTO personPatchDTO = new PersonDTO();
		personPatchDTO.setId(null);
		personPatchDTO.setCode(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setId(ReflectionUtils.getDeleteCodeForLong());
		personPatchDTO.setCode(ReflectionUtils.getDeleteCodeForString());
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSanitaryDocument(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSanitaryDocument(ReflectionUtils.getDeleteCodeForString());
		personDTO.setSanitaryDocument(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setHomePhone(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setHomePhone(ReflectionUtils.getDeleteCodeForString());
		personDTO.setHomePhone("N/A");
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setEmail(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setEmail(ReflectionUtils.getDeleteCodeForString());
		personDTO.setEmail(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setAddressLine2(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setAddressLine2(ReflectionUtils.getDeleteCodeForString());
		personDTO.setAddressLine2(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setBirthDate(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setBirthDate(ReflectionUtils.getDeleteCodeForLocalDate());
		personDTO.setBirthDate(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setUrl1FileName(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setUrl1FileName(ReflectionUtils.getDeleteCodeForString());
		personDTO.setUrl1FileName(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSexId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setSexId(ReflectionUtils.getDeleteCodeForCombosValue());
		personDTO.setSexId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setBloodGroupId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setBloodGroupId(ReflectionUtils.getDeleteCodeForCombosValue());
		personDTO.setBloodGroupId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		log.debug("*************************************************************************");

		personPatchDTO = new PersonDTO();
		personPatchDTO.setCountryBirthId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);

		personPatchDTO = new PersonDTO();
		personPatchDTO.setCountryBirthId(ReflectionUtils.getDeleteCodeForCombosValue());
		personDTO.setCountryBirthId(null);
		patchAndDoComparison(pathParams, personDTO, personPatchDTO);
	}

//	@Test
	public void post_invalid_combo_values() {
		PersonDTO personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setIdentityDocumentTypeId(8888L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setPostalCodeId(888888L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.postal.code.not.exists", e.getMessageKey());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setSexId(8888L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.sex.not.exists", e.getMessageKey());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setBloodGroupId(8888L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.blood.group.not.exists", e.getMessageKey());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setCountryBirthId(8888L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.geographic.location.not.exists", e.getMessageKey());
		}

	}

//	@Test
	public void post_dto_validations() {
		PersonDTO personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setBirthDate(new java.sql.Date(faker.date().future(10, TimeUnit.DAYS).getTime()).toLocalDate());
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (ValidationException e) {
			assertEquals("error.validation.birthDate.Past", e.getMessage());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setAge(-200F);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (ValidationException e) {
			assertEquals("error.validation.age.Min", e.getMessage());
		}

	}

//	@Test
	public void post_integrity_data_based_on_jwt() {
		PersonDTO personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setIdentityDocumentTypeId(10L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
		}

		personDTO = ObjectsBuilderUtils.createFullPersonDTO(faker);
		personDTO.setPostalCodeId(15000L);
		try {
			restTemplate.exchange(buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST,
					new HttpEntity<>(personDTO), PersonDTO.class);
			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.postal.code.not.exists", e.getMessageKey());
		}

	}

//	@Test
	public void patch_integrity_data_based_on_jwt() {
		log.debug("*************************************************************************");

		PersonDTO personDTO = saveFullPerson();

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", personDTO.getId().toString());

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setPostalCodeId(15000L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.postal.code.not.exists", e.getMessageKey());
		}
	}

//	@Test
	public void patch_invalid_combo_values() {
		log.debug("*************************************************************************");

		PersonDTO personDTO = saveFullPerson();

		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", personDTO.getId().toString());

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setCountryBirthId(8888L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.geographic.location.not.exists", e.getMessageKey());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setBloodGroupId(8888L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.blood.group.not.exists", e.getMessageKey());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setSexId(8888L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.sex.not.exists", e.getMessageKey());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO
					.setBirthDate(new java.sql.Date(faker.date().future(10, TimeUnit.DAYS).getTime()).toLocalDate());
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (ValidationException e) {
			assertEquals("error.validation.birthDate.Past", e.getMessage());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setAge(-200F);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (ValidationException e) {
			assertEquals("error.validation.age.Min", e.getMessage());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setPostalCodeId(888888L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.postal.code.not.exists", e.getMessageKey());
		}

		try {
			PersonDTO personPatchDTO = new PersonDTO();
			personPatchDTO.setIdentityDocumentTypeId(8888L);
			patchAndDoComparison(pathParams, personDTO, personPatchDTO);

			assertTrue(false);
		} catch (DataIntegrityException e) {
			assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
		}
	}

	private PersonDTO saveFullPerson() {
		return saveAndAssertPerson(ObjectsBuilderUtils.createFullPersonDTO(faker));
	}

	private PersonDTO saveMinimalPerson() {
		return saveAndAssertPerson(ObjectsBuilderUtils.createMinimalPersonDTO(faker));
	}

	private PersonDTO saveAndAssertPerson(PersonDTO personDTO) {
		ResponseEntity<PersonDTO> savedPersonResponse = restTemplate.exchange(
				buildUriComponent().path(PREFIX).build().toUriString(), HttpMethod.POST, new HttpEntity<>(personDTO),
				PersonDTO.class);

		assertEquals(HttpStatus.CREATED.value(), savedPersonResponse.getStatusCode().value());

		personDTO.setId(savedPersonResponse.getBody().getId());
		personDTO.setCode(savedPersonResponse.getBody().getCode());
		personDTO.setHomePhone(savedPersonResponse.getBody().getHomePhone());
		personDTO.setMunicipalityId(savedPersonResponse.getBody().getMunicipalityId());
		personDTO.setProvinceId(savedPersonResponse.getBody().getProvinceId());
		personDTO.setVersion(savedPersonResponse.getBody().getVersion());

		log.debug("Person created by DTO		:{}", personDTO);
		log.debug("Person saved from response	:{}", savedPersonResponse.getBody());

		assertTrue("No son iguales", EqualsBuilder.reflectionEquals(savedPersonResponse.getBody(), personDTO, false));

		return savedPersonResponse.getBody();
	}

	private void patchAndDoComparison(Map<String, String> pathParams, PersonDTO personDTO, PersonDTO personToPatchDTO) {
		personToPatchDTO = restTemplate
				.exchange(buildUriComponent().path(PREFIX + "/{id}").buildAndExpand(pathParams).toUriString(),
						HttpMethod.PATCH, new HttpEntity<>(personToPatchDTO), PersonDTO.class)
				.getBody();

		personDTO.setVersion(personToPatchDTO.getVersion());

		log.debug("Person in screen     :{}", personDTO);
		log.debug("Person coming in rest:{}", personToPatchDTO);

		assertTrue("No son iguales", EqualsBuilder.reflectionEquals(personToPatchDTO, personDTO, false));
	}

	private UriComponentsBuilder buildUriComponent() {
		return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(serverPort);
	}

}
