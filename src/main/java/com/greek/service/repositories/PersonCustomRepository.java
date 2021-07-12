package com.greek.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.greek.main.hibernate.model.Persona;

public interface PersonCustomRepository {

	Page<Persona> findPersonsLike(@Param("globalFilter") String globalFilter, Pageable p);

}
