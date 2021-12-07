package com.greek.service.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.greek.commons.dto.v1.person.PersonDto;
import com.greek.commons.dto.v1.person.PersonListDto;
import com.greek.main.hibernate.model.Persona;
import com.greek.service.mappers.decorators.PersonMapperDecorator;
import com.gvt.core.reflect.FieldConverter;
import com.gvt.rest.crud.mappers.EntityMapper;

@Mapper(componentModel = "spring")
@DecoratedWith(PersonMapperDecorator.class)
public abstract class PersonMapper implements EntityMapper<PersonDto, PersonListDto, Persona> {

	@Named("fromDtoToEntity")
	@Mapping(source = "code", target = "codigoPersona")
	@Mapping(source = "name", target = "nombrePersona")
	@Mapping(source = "lastName", target = "apellidoPersona")
	@Mapping(source = "identityDocument", target = "cedulaPersona")
	@Mapping(source = "sanitaryDocument", target = "numeroMsas")
	@Mapping(source = "mobilePhone", target = "telefonoMovilPersona")
	@Mapping(source = "homePhone", target = "telefonoFijoPersona")
	@Mapping(source = "email", target = "EMailPersona")
	@Mapping(source = "addressLine1", target = "direccionPersona")
	@Mapping(source = "addressLine2", target = "direccionPersonaCalleAvenida")
	@Mapping(source = "birthDate", target = "fechaNacimientoPersona")
	@Mapping(source = "age", target = "edad")
	@Mapping(source = "identityDocumentTypeId", target = "tipoDocumentoIdentificacion.id")
	@Mapping(source = "postalCodeId", target = "codigoPostal.id")
	@Mapping(source = "sexId", target = "sexo.id")
	@Mapping(source = "bloodGroupId", target = "tipoSangre.id")
	@Mapping(source = "countryBirthId", target = "ubicacionGeograficaByIdUbicacionGeograficaNacimiento.id")
	public abstract Persona fromDtoToEntity(PersonDto personDto);

	@Named("fromDtoToEntityForSave")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@InheritConfiguration(name = "fromDtoToEntity")
	public abstract Persona fromDtoToEntityForSave(PersonDto personDto);

	@InheritInverseConfiguration(name = "fromDtoToEntity")
	public abstract PersonDto fromEntityToDto(Persona persona);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@InheritConfiguration(name = "fromDtoToEntity")
	public abstract Persona mergeDtoIntoEntity(PersonDto personDto, @MappingTarget Persona persona);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@Mapping(source = "bloodGroupId", target = "tipoSangre.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "sexId", target = "sexo.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@InheritConfiguration(name = "fromDtoToEntity")
	public abstract Persona patchDtoIntoEntity(PersonDto personDto, @MappingTarget Persona persona);

	@Mapping(source = "nombrePersona", target = "name")
	@Mapping(source = "apellidoPersona", target = "lastName")
	public abstract PersonListDto fromEntityToListDto(Persona persona);

	@BeforeMapping
	public void doBeforeMapping(@MappingTarget Persona persona, PersonDto personDto) {

		if (personDto.getBloodGroupId() != null) {
			persona.setTipoSangre(null);
		}

		if (personDto.getSexId() != null) {
			persona.setSexo(null);
		}

		if (personDto.getIdentityDocumentTypeId() != null) {
			persona.setTipoDocumentoIdentificacion(null);
		}

		if (personDto.getPostalCodeId() != null) {
			persona.setCodigoPostal(null);
		}

		if (personDto.getCountryBirthId() != null) {
			persona.setUbicacionGeograficaByIdUbicacionGeograficaNacimiento(null);
		}
	}

	@AfterMapping
	public void doAfterMapping(@MappingTarget Persona persona, PersonDto personDto) {
		persona.setNombreContactoUno(null);
		persona.setTelefonoContactoUno(null);
		persona.setParentescoContactoUno(null);

		persona.setNombreContactoDos(null);
		persona.setTelefonoContactoDos(null);
		persona.setParentescoContactoDos(null);

		if (persona.getSexo() != null && (persona.getSexo().getId() == null
				|| persona.getSexo().getId().equals(FieldConverter.getDeleteCodeForCombosValue()))) {
			persona.setSexo(null);
		}

		if (persona.getTipoSangre() != null && (persona.getTipoSangre().getId() == null
				|| persona.getTipoSangre().getId().equals(FieldConverter.getDeleteCodeForCombosValue()))) {
			persona.setTipoSangre(null);
		}

		if (persona.getTipoDocumentoIdentificacion() != null
				&& (persona.getTipoDocumentoIdentificacion().getId() == null || persona.getTipoDocumentoIdentificacion()
						.getId().equals(FieldConverter.getDeleteCodeForCombosValue()))) {
			persona.setTipoDocumentoIdentificacion(null);
		}

		if (persona.getCodigoPostal() != null && (persona.getCodigoPostal().getId() == null
				|| persona.getCodigoPostal().getId().equals(FieldConverter.getDeleteCodeForCombosValue()))) {
			persona.setCodigoPostal(null);
		}

		if (persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento() != null
				&& (persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento().getId() == null
						|| persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento().getId()
								.equals(FieldConverter.getDeleteCodeForCombosValue()))) {
			persona.setUbicacionGeograficaByIdUbicacionGeograficaNacimiento(null);
		}
	}

	@AfterMapping
	public void doAfterMapping(@MappingTarget PersonDto personDto, Persona persona) {
	}

}
