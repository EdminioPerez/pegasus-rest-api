/* AssentSoftware (C)2021 */
package com.greek.service.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;
import com.greek.commons.dto.v1.person.PersonDto;
import com.greek.service.RestServicesApplication;
import com.greek.service.utils.ObjectsBuilderUtils;
import com.gvt.core.exceptions.DataIntegrityException;
import com.gvt.core.reflect.FieldConverter;
import com.gvt.core.response.ErrorResponse;
import com.gvt.rest.utils.ExceptionUtils;
import com.gvt.security.test.context.support.WithMockedUser;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {RestServicesApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT)
@WithMockedUser
@Slf4j
public class PersonRestControllerIT {

    private static final String PREFIX = "/api/v1/persons";

    @LocalServerPort private int serverPort;

    @Autowired private RestTemplate restTemplate;

    @Autowired private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Faker faker = new Faker(new Locale("es", "ES"));
    private UriComponentsBuilder uriComponents;
    private HttpEntity<String> requestEntity;

    @BeforeEach
    public void setUp() {
        //		MockHttpServletRequest request = new MockHttpServletRequest();
        //		request.addHeader(SecurityConstants.HEADER_AUTHORIZATION,
        // JwtTestUtils.builJwtBearerFromMockUser());
        //		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        uriComponents = buildUriComponent();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestEntity = new HttpEntity<>(requestHeaders);
    }

    @Test
    public void save_full_person() {
        // The assertions are in the saveFullPerson
        log.debug("************************");
        this.saveFullPerson();
    }

    @Test
    public void save_minimal_person() {
        // The assertions are in the saveMinimalPerson
        log.debug("************************");
        this.saveMinimalPerson();
    }

    @Test
    public void save_full_person_already_exists() {
        log.debug("************************");
        PersonDto firstSave = this.saveFullPerson();

        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(firstSave),
                    PersonDto.class);

            assertTrue(false);
        } catch (HttpClientErrorException e) {
            Optional<ErrorResponse> errorResponse =
                    ExceptionUtils.convertFromHttpClientErrorException(
                            e, mappingJackson2HttpMessageConverter.getObjectMapper());

            if (errorResponse.isPresent()) {
                assertEquals(
                        "error.person.already.exists.same.organization",
                        errorResponse.get().getMessage());
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void patch_person_ok() throws Exception {
        PersonDto personDto = saveFullPerson();

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", personDto.getId().toString());

        log.debug("*************************************************************************");

        PersonDto personPatchDto = new PersonDto();
        personPatchDto.setId(-123L);
        personPatchDto.setCode("no cambiar");
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setName(faker.name().firstName());
        personDto.setName(personPatchDto.getName());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setLastName(faker.name().lastName() + " " + faker.name().lastName());
        personDto.setLastName(personPatchDto.getLastName());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setIdentityDocumentTypeId(2L);
        personDto.setIdentityDocumentTypeId(personPatchDto.getIdentityDocumentTypeId());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setIdentityDocument(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        personDto.setIdentityDocument(personPatchDto.getIdentityDocument());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setSanitaryDocument(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        personDto.setSanitaryDocument(personPatchDto.getSanitaryDocument());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setMobilePhone(faker.phoneNumber().cellPhone());
        personDto.setMobilePhone(personPatchDto.getMobilePhone());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setHomePhone(faker.phoneNumber().phoneNumber());
        personDto.setHomePhone(personPatchDto.getHomePhone());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setEmail(faker.internet().emailAddress());
        personDto.setEmail(personPatchDto.getEmail());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setAddressLine1(faker.address().fullAddress());
        personPatchDto.setAddressLine2(faker.address().secondaryAddress());
        personDto.setAddressLine1(personPatchDto.getAddressLine1());
        personDto.setAddressLine2(personPatchDto.getAddressLine2());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setProvinceId(1L);
        personPatchDto.setMunicipalityId(44L);
        personPatchDto.setPostalCodeId(100L);
        personDto.setProvinceId(personPatchDto.getProvinceId());
        personDto.setMunicipalityId(personPatchDto.getMunicipalityId());
        personDto.setPostalCodeId(personPatchDto.getPostalCodeId());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        LocalDate birthDate = new java.sql.Date(faker.date().birthday().getTime()).toLocalDate();
        personPatchDto.setBirthDate(birthDate);
        personDto.setBirthDate(personPatchDto.getBirthDate());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setAge((float) Period.between(birthDate, LocalDate.now()).getYears());
        personDto.setAge(personPatchDto.getAge());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setUrl1FileName(faker.file().fileName());
        personDto.setUrl1FileName(personPatchDto.getUrl1FileName());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setSexId(faker.random().nextInt(1, 2).longValue());
        personPatchDto.setBloodGroupId(faker.random().nextInt(1, 8).longValue());
        personPatchDto.setCountryBirthId(faker.random().nextInt(1, 14).longValue());
        personDto.setSexId(personPatchDto.getSexId());
        personDto.setBloodGroupId(personPatchDto.getBloodGroupId());
        personDto.setCountryBirthId(personPatchDto.getCountryBirthId());
        patchAndDoComparison(pathParams, personDto, personPatchDto);
    }

    @Test
    public void patch_person_nullify_ok() throws Exception {

        PersonDto personDto = saveFullPerson();

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", personDto.getId().toString());

        log.debug("*************************************************************************");

        PersonDto personPatchDto = new PersonDto();
        personPatchDto.setId(null);
        personPatchDto.setCode(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setId(FieldConverter.getDeleteCodeForLong());
        personPatchDto.setCode(FieldConverter.getDeleteCodeForString());
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setSanitaryDocument(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setSanitaryDocument(FieldConverter.getDeleteCodeForString());
        personDto.setSanitaryDocument(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setHomePhone(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setHomePhone(FieldConverter.getDeleteCodeForString());
        personDto.setHomePhone("N/A");
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setEmail(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setEmail(FieldConverter.getDeleteCodeForString());
        personDto.setEmail(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setAddressLine2(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setAddressLine2(FieldConverter.getDeleteCodeForString());
        personDto.setAddressLine2(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setBirthDate(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setBirthDate(FieldConverter.getDeleteCodeForLocalDate());
        personDto.setBirthDate(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setUrl1FileName(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setUrl1FileName(FieldConverter.getDeleteCodeForString());
        personDto.setUrl1FileName(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setSexId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setSexId(FieldConverter.getDeleteCodeForCombosValue());
        personDto.setSexId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setBloodGroupId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setBloodGroupId(FieldConverter.getDeleteCodeForCombosValue());
        personDto.setBloodGroupId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        log.debug("*************************************************************************");

        personPatchDto = new PersonDto();
        personPatchDto.setCountryBirthId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);

        personPatchDto = new PersonDto();
        personPatchDto.setCountryBirthId(FieldConverter.getDeleteCodeForCombosValue());
        personDto.setCountryBirthId(null);
        patchAndDoComparison(pathParams, personDto, personPatchDto);
    }

    @Test
    public void post_invalid_combo_values() {
        PersonDto personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setIdentityDocumentTypeId(8888L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setPostalCodeId(888888L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.postal.code.not.exists", e.getMessageKey());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setSexId(8888L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.sex.not.exists", e.getMessageKey());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setBloodGroupId(8888L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.blood.group.not.exists", e.getMessageKey());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setCountryBirthId(8888L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.geographic.location.not.exists", e.getMessageKey());
        }
    }

    @Test
    public void patch_invalid_combo_values() {
        log.debug("*************************************************************************");

        PersonDto personDto = saveFullPerson();

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", personDto.getId().toString());

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setIdentityDocumentTypeId(8888L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setPostalCodeId(888888L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.postal.code.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setSexId(8888L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.sex.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setBloodGroupId(8888L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.blood.group.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setCountryBirthId(8888L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.geographic.location.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setBirthDate(
                    new java.sql.Date(faker.date().future(10, TimeUnit.DAYS).getTime())
                            .toLocalDate());
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals("error.validation.birthDate.Past", e.getMessage());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setAge(-200F);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals("error.validation.age.Min", e.getMessage());
        }
    }

    @Test
    public void post_dto_validations() {
        PersonDto personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setBirthDate(
                new java.sql.Date(faker.date().future(10, TimeUnit.DAYS).getTime()).toLocalDate());
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals("error.validation.birthDate.Past", e.getMessage());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setAge(-200F);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals("error.validation.age.Min", e.getMessage());
        }
    }

    @Test
    public void post_integrity_data_based_on_jwt() {
        PersonDto personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setIdentityDocumentTypeId(10L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
        }

        personDto = ObjectsBuilderUtils.createFullPersonDto(faker);
        personDto.setPostalCodeId(15000L);
        try {
            restTemplate.exchange(
                    buildUriComponent().path(PREFIX).build().toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(personDto),
                    PersonDto.class);
            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.postal.code.not.exists", e.getMessageKey());
        }
    }

    @Test
    public void patch_integrity_data_based_on_jwt() {
        log.debug("*************************************************************************");

        PersonDto personDto = saveFullPerson();

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", personDto.getId().toString());

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setIdentityDocumentTypeId(10L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.identity.document.type.not.exists", e.getMessageKey());
        }

        try {
            PersonDto personPatchDto = new PersonDto();
            personPatchDto.setPostalCodeId(15000L);
            patchAndDoComparison(pathParams, personDto, personPatchDto);

            assertTrue(false);
        } catch (DataIntegrityException e) {
            assertEquals("exception.postal.code.not.exists", e.getMessageKey());
        }
    }

    private PersonDto saveFullPerson() {
        return saveAndAssertPerson(ObjectsBuilderUtils.createFullPersonDto(faker));
    }

    private PersonDto saveMinimalPerson() {
        return saveAndAssertPerson(ObjectsBuilderUtils.createMinimalPersonDto(faker));
    }

    private PersonDto saveAndAssertPerson(PersonDto personDto) {
        ResponseEntity<PersonDto> savedPersonResponse =
                restTemplate.exchange(
                        buildUriComponent().path(PREFIX).build().toUriString(),
                        HttpMethod.POST,
                        new HttpEntity<>(personDto),
                        PersonDto.class);

        assertEquals(HttpStatus.CREATED.value(), savedPersonResponse.getStatusCode().value());

        personDto.setId(savedPersonResponse.getBody().getId());
        personDto.setCode(savedPersonResponse.getBody().getCode());
        personDto.setHomePhone(savedPersonResponse.getBody().getHomePhone());
        personDto.setMunicipalityId(savedPersonResponse.getBody().getMunicipalityId());
        personDto.setProvinceId(savedPersonResponse.getBody().getProvinceId());
        personDto.setVersion(savedPersonResponse.getBody().getVersion());

        log.debug("Person created by Dto		:{}", personDto);
        log.debug("Person saved from response	:{}", savedPersonResponse.getBody());

        assertTrue(EqualsBuilder.reflectionEquals(savedPersonResponse.getBody(), personDto, false));

        return savedPersonResponse.getBody();
    }

    private void patchAndDoComparison(
            Map<String, String> pathParams, PersonDto personDto, PersonDto personToPatchDto) {
        personToPatchDto =
                restTemplate
                        .exchange(
                                buildUriComponent()
                                        .path(PREFIX + "/{id}")
                                        .buildAndExpand(pathParams)
                                        .toUriString(),
                                HttpMethod.PATCH,
                                new HttpEntity<>(personToPatchDto),
                                PersonDto.class)
                        .getBody();

        personDto.setVersion(personToPatchDto.getVersion());

        log.debug("Person in screen     :{}", personDto);
        log.debug("Person coming in rest:{}", personToPatchDto);

        assertTrue(EqualsBuilder.reflectionEquals(personToPatchDto, personDto, false));
    }

    private UriComponentsBuilder buildUriComponent() {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(serverPort);
    }
}
