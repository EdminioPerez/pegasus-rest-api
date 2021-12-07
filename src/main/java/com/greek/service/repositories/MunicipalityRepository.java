/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.Poblacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<Poblacion, Long> {

    @Query(
            "select distinct(p) from Poblacion p join p.codigoPostals cp where cp.provincia.id = :provinciaId order by p.nombrePoblacion asc")
    List<Poblacion> findMunicipalities(@Param("provinciaId") long provinciaId);
}
