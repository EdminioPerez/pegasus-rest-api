package com.greek.service.manager;

import java.util.List;

import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Poblacion;
import com.greek.main.hibernate.model.Provincia;
import com.greek.main.hibernate.model.Sexo;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.main.hibernate.model.TipoSangre;
import com.greek.main.hibernate.model.UbicacionGeografica;

public interface SimpleDomainService {

	List<TipoSangre> findBloodGroups();

	List<Sexo> findSex();

	List<Provincia> findProvinces();

	List<Poblacion> findMunicipalities(Long provinceId);

	List<CodigoPostal> findPostalCodes(Long provinceId, Long municipialityId);

	CodigoPostal findPostalCodeById(Long id);

	List<TipoDocumentoIdentificacion> findIdentityDocumentsTypeByCountry();

	List<UbicacionGeografica> findAllGeographicLocations();

}
