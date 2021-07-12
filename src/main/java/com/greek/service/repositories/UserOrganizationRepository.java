package com.greek.service.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.UsuarioOrganizacion;

@Repository
public interface UserOrganizationRepository extends PagingAndSortingRepository<UsuarioOrganizacion, Long> {

	List<UsuarioOrganizacion> findByPersonaId(@Param("personaId") Long personaId);

}
