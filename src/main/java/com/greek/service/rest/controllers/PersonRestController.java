/* AssentSoftware (C)2021 */
package com.greek.service.rest.controllers;

import com.greek.commons.dto.v1.person.PersonDto;
import com.greek.commons.dto.v1.person.PersonListDto;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Persona;
import com.greek.service.manager.PersonService;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.PersonMapper;
import com.gvt.rest.crud.controllers.CrudRestController;
import javax.validation.Valid;
import javax.validation.Validator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonRestController extends CrudRestController<PersonDto, PersonListDto, Persona> {

    private final SimpleDomainService simpleDomainService;
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonRestController(
            PersonService personService,
            SimpleDomainService simpleDomainService,
            PersonMapper personMapper,
            Validator validator) {
        super(personService, personMapper, validator);

        this.personService = personService;
        this.simpleDomainService = simpleDomainService;
        this.personMapper = personMapper;
    }

    @Override
    public PersonDto save(@RequestBody @Valid PersonDto dto) {
        PersonDto savedPersonDto = null;

        try {
            savedPersonDto = super.save(dto);
        } catch (DataIntegrityViolationException e) {
            personService.checkPersonIsPresentBehaviour(
                    personMapper.fromDtoToEntityForSave(dto), e);
        }

        if (savedPersonDto != null) {
            savedPersonDto.setProvinceId(dto.getProvinceId());
            savedPersonDto.setMunicipalityId(dto.getMunicipalityId());
        }

        return savedPersonDto;
    }

    @Override
    public PersonDto patch(@PathVariable("id") Long id, @RequestBody PersonDto dto) {
        PersonDto updatedEntity = super.patch(id, dto);

        CodigoPostal postalCode =
                simpleDomainService.findPostalCodeById(updatedEntity.getPostalCodeId());

        updatedEntity.setProvinceId(postalCode.getProvincia().getId());
        updatedEntity.setMunicipalityId(postalCode.getPoblacion().getId());

        return updatedEntity;
    }
}
