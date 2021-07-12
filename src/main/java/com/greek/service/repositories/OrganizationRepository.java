package com.greek.service.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.Organizacion;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organizacion, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("from Organizacion o where o.id = :id")
	Organizacion findOrganizationForWrite(@Param("id") Long id);

	Organizacion findByRifOrganizacion(@Param("rifOrganizacion") String rifOrganizacion);

}
