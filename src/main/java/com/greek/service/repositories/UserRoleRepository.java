package com.greek.service.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.RolUsuario;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<RolUsuario, Long> {

	List<RolUsuario> findByUsuarioId(@Param("usuarioId") Long usuarioId);

}
