package com.greek.service.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.greek.commons.dto.v1.simple.SimpleDTO;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Poblacion;
import com.greek.main.hibernate.model.Provincia;
import com.greek.main.hibernate.model.Sexo;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.main.hibernate.model.TipoSangre;
import com.greek.main.hibernate.model.UbicacionGeografica;

@Mapper(componentModel = "spring")
public interface SimpleDomainMapper {

	/** ---------- BloodGroup Mapping ---------- **/

	@Named(value = "bloodGroupNormalMapping")
	@Mapping(source = "codigoTipoSangre", target = "code")
	@Mapping(source = "nombreTipoSangre", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromBloodGroupToDto(TipoSangre tipoSangre);

	@Mapping(source = "codigoTipoSangre", target = "code")
	@Mapping(source = "nombreTipoSangre", target = "description")
	SimpleDTO fromBloodGroupToFullDto(TipoSangre tipoSangre);

	@IterableMapping(qualifiedByName = "bloodGroupNormalMapping")
	List<SimpleDTO> fromBloodGroupToDtos(List<TipoSangre> tiposSangre);

	/** ---------- Sex Mapping ---------- **/

	@Named(value = "sexNormalMapping")
	@Mapping(source = "codigoSexo", target = "code")
	@Mapping(source = "nombreSexo", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromSexToDto(Sexo sexo);

	@Mapping(source = "codigoSexo", target = "code")
	@Mapping(source = "nombreSexo", target = "description")
	SimpleDTO fromSexToFullDto(Sexo sexo);

	@IterableMapping(qualifiedByName = "sexNormalMapping")
	List<SimpleDTO> fromSexToDtos(List<Sexo> sexo);

	/** ---------- Province Mapping ---------- **/

	@Named(value = "provinceNormalMapping")
	@Mapping(source = "codigoProvincia", target = "code")
	@Mapping(source = "nombreProvincia", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromProvinceToDto(Provincia provincia);

	@Mapping(source = "codigoProvincia", target = "code")
	@Mapping(source = "nombreProvincia", target = "description")
	SimpleDTO fromProvinceToFullDto(Provincia provincia);

	@IterableMapping(qualifiedByName = "provinceNormalMapping")
	List<SimpleDTO> fromProvinceToDtos(List<Provincia> provincias);

	/** ---------- Municipality Mapping ---------- **/

	@Named(value = "municipalityNormalMapping")
	@Mapping(source = "codigoPoblacion", target = "code")
	@Mapping(source = "nombrePoblacion", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromMunicipalityToDto(Poblacion poblacion);

	@Mapping(source = "codigoPoblacion", target = "code")
	@Mapping(source = "nombrePoblacion", target = "description")
	SimpleDTO fromMunicipalityToFullDto(Poblacion poblacion);

	@IterableMapping(qualifiedByName = "municipalityNormalMapping")
	List<SimpleDTO> fromMunicipalityToDtos(List<Poblacion> poblaciones);

	/** ---------- Postal codes Mapping ---------- **/

	@Named(value = "postalCodeNormalMapping")
	@Mapping(source = "codigoPostal", target = "code")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromPostalCodeToDto(CodigoPostal codigoPostal);

	@Mapping(source = "codigoPostal", target = "code")
	SimpleDTO fromPostalCodeToFullDto(CodigoPostal codigoPostal);

	@IterableMapping(qualifiedByName = "postalCodeNormalMapping")
	List<SimpleDTO> fromPostalCodesToDtos(List<CodigoPostal> codigosPostales);

	/** ---------- Identity documents types Mapping ---------- **/

	@Named(value = "identityDocumentsTypesNormalMapping")
	@Mapping(source = "codigoTipoDocumentoIdentificacion", target = "code")
	@Mapping(source = "nombreTipoDocumentoIdentificacion", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromIdentityDocumentsTypesToDto(TipoDocumentoIdentificacion tipoDocumentoIdentificacion);

	@Mapping(source = "codigoTipoDocumentoIdentificacion", target = "code")
	@Mapping(source = "nombreTipoDocumentoIdentificacion", target = "description")
	SimpleDTO fromIdentityDocumentsTypesToFullDto(TipoDocumentoIdentificacion tipoDocumentoIdentificacion);

	@IterableMapping(qualifiedByName = "identityDocumentsTypesNormalMapping")
	List<SimpleDTO> fromIdentityDocumentsTypesToDtos(List<TipoDocumentoIdentificacion> tiposDocumentosIdentificacion);

	/** ---------- GeographicLocation Mapping ---------- **/

	@Named(value = "geographicLocationNormalMapping")
	@Mapping(source = "codigoUbicacionGeografica", target = "code")
	@Mapping(source = "nombreUbicacionGeografica", target = "description")
	@Mapping(target = "version", ignore = true)
	SimpleDTO fromGeographicLocationToDto(UbicacionGeografica ubicacionGeografica);

	@Mapping(source = "codigoUbicacionGeografica", target = "code")
	@Mapping(source = "nombreUbicacionGeografica", target = "description")
	SimpleDTO fromGeographicLocationToFullDto(UbicacionGeografica ubicacionGeografica);

	@IterableMapping(qualifiedByName = "geographicLocationNormalMapping")
	List<SimpleDTO> fromGeographicLocationToDtos(List<UbicacionGeografica> ubicacionGeografica);

}
