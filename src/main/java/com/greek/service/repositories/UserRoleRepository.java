package com.greek.service.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.RolUsuario;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<RolUsuario, Long> {

}
