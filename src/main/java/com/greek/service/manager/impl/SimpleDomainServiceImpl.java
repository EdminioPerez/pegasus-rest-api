/* AssentSoftware (C)2021 */
package com.greek.service.manager.impl;

import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Poblacion;
import com.greek.main.hibernate.model.Provincia;
import com.greek.main.hibernate.model.Sexo;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.main.hibernate.model.TipoSangre;
import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.repositories.BloodGroupsRepository;
import com.greek.service.repositories.GeographicLocationRepository;
import com.greek.service.repositories.IdentityDocumentTypeRepository;
import com.greek.service.repositories.MunicipalityRepository;
import com.greek.service.repositories.PostalCodesRepository;
import com.greek.service.repositories.ProvinceRepository;
import com.greek.service.repositories.SexRepository;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimpleDomainServiceImpl implements SimpleDomainService {

    private final BloodGroupsRepository bloodGroupsRepository;
    private final SexRepository sexRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final PostalCodesRepository postalCodesRepository;
    private final IdentityDocumentTypeRepository identityDocumentTypeRepository;
    private final GeographicLocationRepository geographicLocationRepository;
    private final MessageSource messageSource;

    @Override
    public List<TipoSangre> findBloodGroups() {
        return bloodGroupsRepository.findAll();
    }

    @Override
    public List<Sexo> findSex() {
        List<Sexo> sexoItems = sexRepository.findAll();

        for (Sexo sexo : sexoItems) {
            sexo.setNombreSexo(
                    messageSource.getMessage(
                            "lookup.sex." + sexo.getCodigoSexo(),
                            null,
                            LocaleContextHolder.getLocale()));
        }

        return sexoItems;
    }

    @Override
    public List<Provincia> findProvinces() {
        return provinceRepository.findProvinces();
    }

    @Override
    public List<Poblacion> findMunicipalities(Long provinceId) {
        return municipalityRepository.findMunicipalities(provinceId);
    }

    @Override
    public List<CodigoPostal> findPostalCodes(Long provinceId, Long municipialityId) {
        return postalCodesRepository.findAll(provinceId, municipialityId);
    }

    @Override
    public CodigoPostal findPostalCodeById(Long id) {
        return postalCodesRepository.findById(id).orElse(null);
    }

    public List<TipoDocumentoIdentificacion> findIdentityDocumentsTypeByCountry() {
        return identityDocumentTypeRepository.findAll();
    }

    @Override
    public List<UbicacionGeografica> findAllGeographicLocations() {
        List<UbicacionGeografica> ubicacionGeograficaItems =
                geographicLocationRepository.findByUbicacionGeograficaIsNull();

        for (UbicacionGeografica ubicacionGeografica : ubicacionGeograficaItems) {
            ubicacionGeografica.setNombreUbicacionGeografica(
                    messageSource.getMessage(
                            "lookup.geographic.location."
                                    + ubicacionGeografica.getCodigoUbicacionGeografica(),
                            null,
                            LocaleContextHolder.getLocale()));
        }

        ubicacionGeograficaItems.sort(
                Comparator.comparing(
                        UbicacionGeografica::getNombreUbicacionGeografica, Collator.getInstance()));

        return ubicacionGeograficaItems;
    }
}
