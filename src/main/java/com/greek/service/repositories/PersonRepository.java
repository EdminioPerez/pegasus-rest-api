/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.Persona;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
        extends PagingAndSortingRepository<Persona, Long>, PersonCustomRepository {

    @Override
    //	@Query("from Persona p where p.id = :id and p.organizacion.id =
    // :#{authentication.details.rootCenterId}")
    Optional<Persona> findById(@Param("id") Long id);

    //	@Query("from Persona p where p.EMailPersona = :emailPersona and p.organizacion.id =
    // :#{principal.rootCenterId}")
    List<Persona> findByEMailPersona(@Param("emailPersona") String emailPersona);

    @Query(
            "select id from Persona p where p.tipoDocumentoIdentificacion.id = :tipoDocumentoIdentificacionId and p.cedulaPersona = :cedulaPersona and p.ubicacionGeograficaByIdPais.id = :paisId")
    Optional<Long> findByGlobalConstraint(
            @Param("tipoDocumentoIdentificacionId") Long tipoDocumentoIdentificacionId,
            @Param("cedulaPersona") String cedulaPersona,
            @Param("paisId") Long paisId);
}
