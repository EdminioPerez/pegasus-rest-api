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

import com.greek.commons.dto.v1.person.PersonDTO;
import com.greek.commons.dto.v1.person.PersonListDTO;
import com.greek.main.hibernate.model.Persona;
import com.greek.service.mappers.decorators.PersonMapperDecorator;
import com.gvt.core.reflect.ReflectionUtils;
import com.gvt.rest.crud.mappers.EntityMapper;

@Mapper(componentModel = "spring")
@DecoratedWith(PersonMapperDecorator.class)
public abstract class PersonMapper implements EntityMapper<PersonDTO, PersonListDTO, Persona> {

	@Named("fromDTOToEntity")
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
	public abstract Persona fromDTOToEntity(PersonDTO personDTO);

	@Named("fromDTOToEntityForSave")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@InheritConfiguration(name = "fromDTOToEntity")
	public abstract Persona fromDTOToEntityForSave(PersonDTO personDTO);

	@InheritInverseConfiguration(name = "fromDTOToEntity")
	public abstract PersonDTO fromEntityToDTO(Persona persona);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@InheritConfiguration(name = "fromDTOToEntity")
	public abstract Persona mergeDTOIntoEntity(PersonDTO personDTO, @MappingTarget Persona persona);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "codigoPersona", ignore = true)
	@Mapping(source = "bloodGroupId", target = "tipoSangre.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "sexId", target = "sexo.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@InheritConfiguration(name = "fromDTOToEntity")
	public abstract Persona patchDTOIntoEntity(PersonDTO personDTO, @MappingTarget Persona persona);

	@Mapping(source = "nombrePersona", target = "name")
	@Mapping(source = "apellidoPersona", target = "lastName")
	public abstract PersonListDTO fromEntityToListDTO(Persona persona);

	@BeforeMapping
	public void doBeforeMapping(@MappingTarget Persona persona, PersonDTO personDTO) {

		if (personDTO.getBloodGroupId() != null) {
			persona.setTipoSangre(null);
		}

		if (personDTO.getSexId() != null) {
			persona.setSexo(null);
		}

		if (personDTO.getIdentityDocumentTypeId() != null) {
			persona.setTipoDocumentoIdentificacion(null);
		}

		if (personDTO.getPostalCodeId() != null) {
			persona.setCodigoPostal(null);
		}

		if (personDTO.getCountryBirthId() != null) {
			persona.setUbicacionGeograficaByIdUbicacionGeograficaNacimiento(null);
		}
	}

	@AfterMapping
	public void doAfterMapping(@MappingTarget Persona persona, PersonDTO personDTO) {
		persona.setNombreContactoUno(null);
		persona.setTelefonoContactoUno(null);
		persona.setParentescoContactoUno(null);

		persona.setNombreContactoDos(null);
		persona.setTelefonoContactoDos(null);
		persona.setParentescoContactoDos(null);

		if (persona.getSexo() != null && (persona.getSexo().getId() == null
				|| persona.getSexo().getId().equals(ReflectionUtils.getDeleteCodeForCombosValue()))) {
			persona.setSexo(null);
		}

		if (persona.getTipoSangre() != null && (persona.getTipoSangre().getId() == null
				|| persona.getTipoSangre().getId().equals(ReflectionUtils.getDeleteCodeForCombosValue()))) {
			persona.setTipoSangre(null);
		}

		if (persona.getTipoDocumentoIdentificacion() != null
				&& (persona.getTipoDocumentoIdentificacion().getId() == null || persona.getTipoDocumentoIdentificacion()
						.getId().equals(ReflectionUtils.getDeleteCodeForCombosValue()))) {
			persona.setTipoDocumentoIdentificacion(null);
		}

		if (persona.getCodigoPostal() != null && (persona.getCodigoPostal().getId() == null
				|| persona.getCodigoPostal().getId().equals(ReflectionUtils.getDeleteCodeForCombosValue()))) {
			persona.setCodigoPostal(null);
		}

		if (persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento() != null
				&& (persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento().getId() == null
						|| persona.getUbicacionGeograficaByIdUbicacionGeograficaNacimiento().getId()
								.equals(ReflectionUtils.getDeleteCodeForCombosValue()))) {
			persona.setUbicacionGeograficaByIdUbicacionGeograficaNacimiento(null);
		}
	}

	@AfterMapping
	public void doAfterMapping(@MappingTarget PersonDTO personDTO, Persona persona) {
	}

}
