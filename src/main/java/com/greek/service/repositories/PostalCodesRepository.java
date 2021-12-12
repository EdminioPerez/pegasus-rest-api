/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.CodigoPostal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodesRepository extends JpaRepository<CodigoPostal, Long> {

    @Query(
            "from CodigoPostal cp where cp.provincia.id = :provinciaId and cp.poblacion.id = :poblacionId and cp.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.countryCode} order by cp.codigoPostal asc")
    List<CodigoPostal> findAll(
            @Param("provinciaId") Long provinciaId, @Param("poblacionId") Long poblacionId);

    @Query(
            "from CodigoPostal p where p.id = :id and p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.countryCode}")
    Optional<CodigoPostal> findById(@Param("id") Long id);
}
