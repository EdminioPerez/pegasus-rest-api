package com.greek.service.mappers.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.greek.service.mappers.PersonMapper;
import com.greek.commons.dto.v1.person.PersonDTO;
import com.gvt.core.reflect.ReflectionUtils;
import com.greek.main.hibernate.model.Persona;

public abstract class PersonMapperDecorator extends PersonMapper {

	@Autowired
	@Qualifier("delegate")
	private PersonMapper delegate;

	@Override
	public Persona patchDTOIntoEntity(PersonDTO personDTO, Persona persona) {
		delegate.patchDTOIntoEntity(personDTO, persona);

		ReflectionUtils.replaceFieldsToRemove(persona);

		return persona;
	}

}
