package com.greek.service.rest.controllers;

import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greek.commons.dto.v1.person.PersonDTO;
import com.greek.commons.dto.v1.person.PersonListDTO;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Persona;
import com.greek.service.manager.PersonService;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.PersonMapper;
import com.gvt.rest.crud.controllers.CrudRestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/persons")
@Slf4j
public class PersonRestController extends CrudRestController<PersonDTO, PersonListDTO, Persona> {

	private SimpleDomainService simpleDomainService;
	private PersonService personService;
	private PersonMapper personMapper;

	public PersonRestController(PersonService personService, SimpleDomainService simpleDomainService,
			PersonMapper personMapper, Validator validator) {
		super(personService, personMapper, validator);

		this.personService = personService;
		this.simpleDomainService = simpleDomainService;
		this.personMapper = personMapper;
	}

	@Override
	public PersonDTO save(@RequestBody @Valid PersonDTO dto) {
		PersonDTO savedEntity = null;

		try {
			savedEntity = super.save(dto);
		} catch (DataIntegrityViolationException e) {
			personService.checkPersonIsPresentBehaviour(personMapper.fromDTOToEntityForSave(dto), e);
		}

		if (savedEntity != null) {
			savedEntity.setProvinceId(dto.getProvinceId());
			savedEntity.setMunicipalityId(dto.getMunicipalityId());
		}

		return savedEntity;
	}

	@Override
	public PersonDTO patch(Long id, PersonDTO dto) {
		PersonDTO updatedEntity = super.patch(id, dto);

		CodigoPostal postalCode = simpleDomainService.findPostalCodeById(updatedEntity.getPostalCodeId());

		updatedEntity.setProvinceId(postalCode.getProvincia().getId());
		updatedEntity.setMunicipalityId(postalCode.getPoblacion().getId());

		return updatedEntity;
	}

}
