/* AssentSoftware (C)2021 */
package com.greek.service.utils;

import com.github.javafaker.Faker;
import com.greek.commons.dto.v1.person.PersonDto;
import java.time.LocalDate;
import java.time.Period;
import org.apache.commons.lang3.RandomStringUtils;

public class ObjectsBuilderUtils {

    private ObjectsBuilderUtils() {
        // Utility class
    }

    public static PersonDto createFullPersonDto(Faker faker) {
        PersonDto personDto = new PersonDto();
        personDto.setName(faker.name().firstName());
        personDto.setLastName(faker.name().lastName() + " " + faker.name().lastName());
        personDto.setIdentityDocumentTypeId(1L);
        personDto.setIdentityDocument(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        personDto.setSanitaryDocument(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        personDto.setMobilePhone(faker.phoneNumber().cellPhone());
        personDto.setHomePhone(faker.phoneNumber().phoneNumber());
        personDto.setEmail(faker.internet().emailAddress());
        personDto.setAddressLine1(faker.address().fullAddress());
        personDto.setAddressLine2(faker.address().secondaryAddress());
        //		personDto.setProvinceId(14L);
        //		personDto.setMunicipalityId(872L);
        personDto.setPostalCodeId(faker.random().nextInt(1, 14665).longValue()); // 1476L
        LocalDate birthDate = new java.sql.Date(faker.date().birthday().getTime()).toLocalDate();
        personDto.setBirthDate(birthDate);
        personDto.setAge((float) Period.between(birthDate, LocalDate.now()).getYears());
        personDto.setUrl1FileName(faker.file().fileName());
        personDto.setSexId(faker.random().nextInt(1, 2).longValue());
        personDto.setBloodGroupId(faker.random().nextInt(1, 8).longValue());
        personDto.setCountryBirthId(faker.random().nextInt(1, 14).longValue());

        return personDto;
    }

    public static PersonDto createMinimalPersonDto(Faker faker) {
        PersonDto personDto = new PersonDto();
        personDto.setName(faker.name().firstName());
        personDto.setLastName(faker.name().lastName() + " " + faker.name().lastName());
        personDto.setIdentityDocumentTypeId(1L);
        personDto.setIdentityDocument(
                RandomStringUtils.randomAlphanumeric(1)
                        + "-"
                        + RandomStringUtils.randomNumeric(14));
        personDto.setMobilePhone(faker.phoneNumber().cellPhone());
        personDto.setAddressLine1(faker.address().fullAddress());
        personDto.setPostalCodeId(faker.random().nextInt(1, 14665).longValue()); // 1476L

        return personDto;
    }
}
