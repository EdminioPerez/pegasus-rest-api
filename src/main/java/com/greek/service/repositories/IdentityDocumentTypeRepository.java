package com.greek.service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;

@Repository
public interface IdentityDocumentTypeRepository extends JpaRepository<TipoDocumentoIdentificacion, Long> {

	@Query("from TipoDocumentoIdentificacion p where p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode}")
	List<TipoDocumentoIdentificacion> findAll();

	@Query("from TipoDocumentoIdentificacion p where p.id = :id and p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode}")
	Optional<TipoDocumentoIdentificacion> findById(@Param("id") Long id);

}
