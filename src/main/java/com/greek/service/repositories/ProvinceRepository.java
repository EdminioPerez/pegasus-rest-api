/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.Provincia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Provincia, Long> {

    @Query(
            "select distinct(p) from Provincia p inner join p.codigoPostals cp where cp.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.countryCode} order by p.nombreProvincia asc")
    List<Provincia> findProvinces();
}
