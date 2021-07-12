package com.greek.service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.CodigoPostal;

@Repository
public interface PostalCodesRepository extends JpaRepository<CodigoPostal, Long> {

	@Query("from CodigoPostal cp where cp.provincia.id = :provinciaId and cp.poblacion.id = :poblacionId and cp.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode} order by cp.codigoPostal asc")
	List<CodigoPostal> findAll(@Param("provinciaId") Long provinciaId, @Param("poblacionId") Long poblacionId);

	@Query("from CodigoPostal p where p.id = :id and p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode}")
	Optional<CodigoPostal> findById(@Param("id") Long id);

}
