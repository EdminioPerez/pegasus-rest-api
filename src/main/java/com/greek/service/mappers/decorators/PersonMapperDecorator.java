/* AssentSoftware (C)2021 */
package com.greek.service.mappers.decorators;

import com.greek.commons.dto.v1.person.PersonDto;
import com.greek.main.hibernate.model.Persona;
import com.greek.service.mappers.PersonMapper;
import com.gvt.core.reflect.FieldConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PersonMapperDecorator extends PersonMapper {

    @Autowired
    @Qualifier("delegate")
    private PersonMapper delegate;

    private FieldConverter fieldConverter = new FieldConverter();

    @Override
    public Persona patchDtoIntoEntity(PersonDto personDto, Persona persona) {
        delegate.patchDtoIntoEntity(personDto, persona);

        fieldConverter.replaceFieldsToRemove(persona);

        return persona;
    }
}
