/* AssentSoftware (C)2021 */
package com.greek.service.mappers;

import com.greek.commons.dto.v1.simple.SimpleDto;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Poblacion;
import com.greek.main.hibernate.model.Provincia;
import com.greek.main.hibernate.model.Sexo;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.main.hibernate.model.TipoSangre;
import com.greek.main.hibernate.model.UbicacionGeografica;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SimpleDomainMapper {

    /** ---------- BloodGroup Mapping ---------- * */
    @Named(value = "bloodGroupNormalMapping")
    @Mapping(source = "codigoTipoSangre", target = "code")
    @Mapping(source = "nombreTipoSangre", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromBloodGroupToDto(TipoSangre tipoSangre);

    @Mapping(source = "codigoTipoSangre", target = "code")
    @Mapping(source = "nombreTipoSangre", target = "description")
    SimpleDto fromBloodGroupToFullDto(TipoSangre tipoSangre);

    @IterableMapping(qualifiedByName = "bloodGroupNormalMapping")
    List<SimpleDto> fromBloodGroupToDtos(List<TipoSangre> tiposSangre);

    /** ---------- Sex Mapping ---------- * */
    @Named(value = "sexNormalMapping")
    @Mapping(source = "codigoSexo", target = "code")
    @Mapping(source = "nombreSexo", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromSexToDto(Sexo sexo);

    @Mapping(source = "codigoSexo", target = "code")
    @Mapping(source = "nombreSexo", target = "description")
    SimpleDto fromSexToFullDto(Sexo sexo);

    @IterableMapping(qualifiedByName = "sexNormalMapping")
    List<SimpleDto> fromSexToDtos(List<Sexo> sexo);

    /** ---------- Province Mapping ---------- * */
    @Named(value = "provinceNormalMapping")
    @Mapping(source = "codigoProvincia", target = "code")
    @Mapping(source = "nombreProvincia", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromProvinceToDto(Provincia provincia);

    @Mapping(source = "codigoProvincia", target = "code")
    @Mapping(source = "nombreProvincia", target = "description")
    SimpleDto fromProvinceToFullDto(Provincia provincia);

    @IterableMapping(qualifiedByName = "provinceNormalMapping")
    List<SimpleDto> fromProvinceToDtos(List<Provincia> provincias);

    /** ---------- Municipality Mapping ---------- * */
    @Named(value = "municipalityNormalMapping")
    @Mapping(source = "codigoPoblacion", target = "code")
    @Mapping(source = "nombrePoblacion", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromMunicipalityToDto(Poblacion poblacion);

    @Mapping(source = "codigoPoblacion", target = "code")
    @Mapping(source = "nombrePoblacion", target = "description")
    SimpleDto fromMunicipalityToFullDto(Poblacion poblacion);

    @IterableMapping(qualifiedByName = "municipalityNormalMapping")
    List<SimpleDto> fromMunicipalityToDtos(List<Poblacion> poblaciones);

    /** ---------- Postal codes Mapping ---------- * */
    @Named(value = "postalCodeNormalMapping")
    @Mapping(source = "codigoPostal", target = "code")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromPostalCodeToDto(CodigoPostal codigoPostal);

    @Mapping(source = "codigoPostal", target = "code")
    SimpleDto fromPostalCodeToFullDto(CodigoPostal codigoPostal);

    @IterableMapping(qualifiedByName = "postalCodeNormalMapping")
    List<SimpleDto> fromPostalCodesToDtos(List<CodigoPostal> codigosPostales);

    /** ---------- Identity documents types Mapping ---------- * */
    @Named(value = "identityDocumentsTypesNormalMapping")
    @Mapping(source = "codigoTipoDocumentoIdentificacion", target = "code")
    @Mapping(source = "nombreTipoDocumentoIdentificacion", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromIdentityDocumentsTypesToDto(
            TipoDocumentoIdentificacion tipoDocumentoIdentificacion);

    @Mapping(source = "codigoTipoDocumentoIdentificacion", target = "code")
    @Mapping(source = "nombreTipoDocumentoIdentificacion", target = "description")
    SimpleDto fromIdentityDocumentsTypesToFullDto(
            TipoDocumentoIdentificacion tipoDocumentoIdentificacion);

    @IterableMapping(qualifiedByName = "identityDocumentsTypesNormalMapping")
    List<SimpleDto> fromIdentityDocumentsTypesToDtos(
            List<TipoDocumentoIdentificacion> tiposDocumentosIdentificacion);

    /** ---------- GeographicLocation Mapping ---------- * */
    @Named(value = "geographicLocationNormalMapping")
    @Mapping(source = "codigoUbicacionGeografica", target = "code")
    @Mapping(source = "nombreUbicacionGeografica", target = "description")
    @Mapping(target = "version", ignore = true)
    SimpleDto fromGeographicLocationToDto(UbicacionGeografica ubicacionGeografica);

    @Mapping(source = "codigoUbicacionGeografica", target = "code")
    @Mapping(source = "nombreUbicacionGeografica", target = "description")
    SimpleDto fromGeographicLocationToFullDto(UbicacionGeografica ubicacionGeografica);

    @IterableMapping(qualifiedByName = "geographicLocationNormalMapping")
    List<SimpleDto> fromGeographicLocationToDtos(List<UbicacionGeografica> ubicacionGeografica);
}
