package com.greek.service.utils;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.javafaker.Faker;
import com.greek.commons.dto.v1.person.PersonDTO;

public class ObjectsBuilderUtils {

	private ObjectsBuilderUtils() {
		// Utility class
	}

	public static PersonDTO createFullPersonDTO(Faker faker) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setName(faker.name().firstName());
		personDTO.setLastName(faker.name().lastName() + " " + faker.name().lastName());
		personDTO.setIdentityDocumentTypeId(2L);
		personDTO.setIdentityDocument(
				RandomStringUtils.randomAlphanumeric(1) + "-" + RandomStringUtils.randomNumeric(14));
		personDTO.setSanitaryDocument(
				RandomStringUtils.randomAlphanumeric(1) + "-" + RandomStringUtils.randomNumeric(14));
		personDTO.setMobilePhone(faker.phoneNumber().cellPhone());
		personDTO.setHomePhone(faker.phoneNumber().phoneNumber());
		personDTO.setEmail(faker.internet().emailAddress());
		personDTO.setAddressLine1(faker.address().fullAddress());
		personDTO.setAddressLine2(faker.address().secondaryAddress());
		personDTO.setProvinceId(14L);
		personDTO.setMunicipalityId(872L);
		personDTO.setPostalCodeId(1476L);
		personDTO.setBirthDate(new java.sql.Date(faker.date().birthday().getTime()).toLocalDate());
		personDTO.setUrl1FileName(faker.file().fileName());
		personDTO.setSexId(1L);
		personDTO.setBloodGroupId(1L);
		personDTO.setCountryBirthId(12L);

		return personDTO;
	}

}
