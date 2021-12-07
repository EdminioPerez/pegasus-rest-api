/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityDocumentTypeRepository
        extends JpaRepository<TipoDocumentoIdentificacion, Long> {

    @Query(
            "from TipoDocumentoIdentificacion p where p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode}")
    List<TipoDocumentoIdentificacion> findAll();

    @Query(
            "from TipoDocumentoIdentificacion p where p.id = :id and p.ubicacionGeografica.codigoUbicacionGeografica = :#{authentication.details.countryCode}")
    Optional<TipoDocumentoIdentificacion> findById(@Param("id") Long id);
}
