package com.greek.service.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.UsuarioOrganizacion;

@Repository
public interface UserOrganizationRepository extends PagingAndSortingRepository<UsuarioOrganizacion, Long> {

}
