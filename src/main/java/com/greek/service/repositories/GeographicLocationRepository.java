package com.greek.service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.UbicacionGeografica;

@Repository
public interface GeographicLocationRepository extends JpaRepository<UbicacionGeografica, Long> {

	Optional<UbicacionGeografica> findByCodigoUbicacionGeografica(String code);

	List<UbicacionGeografica> findByUbicacionGeograficaIsNull();

}
