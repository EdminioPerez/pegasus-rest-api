/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.PersonaOrganizacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonOrganizationRepository extends JpaRepository<PersonaOrganizacion, Long> {

    @Query(
            "from PersonaOrganizacion p where p.persona.id = :personaId and p.organizacion.id = :#{authentication.rootCenterId}")
    Optional<PersonaOrganizacion> findByPersonaId(@Param("personaId") Long personaId);
}
