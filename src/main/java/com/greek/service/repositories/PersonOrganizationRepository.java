package com.greek.service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.PersonaOrganizacion;

@Repository
public interface PersonOrganizationRepository extends JpaRepository<PersonaOrganizacion, Long> {

	@Query("from PersonaOrganizacion p where p.persona.id = :personaId and p.organizacion.id = :#{principal.details.rootCenterId}")
	Optional<PersonaOrganizacion> findByPersonaId(@Param("personaId") Long personaId);
}
