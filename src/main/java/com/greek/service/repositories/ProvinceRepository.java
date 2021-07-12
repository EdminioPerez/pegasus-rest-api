package com.greek.service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.Provincia;

@Repository
public interface ProvinceRepository extends JpaRepository<Provincia, Long> {

	@Query("select distinct(p) from Provincia p inner join p.codigoPostals cp where cp.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode} order by p.nombreProvincia asc")
	List<Provincia> findProvinces();

}
