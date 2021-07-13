package com.greek.service.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.Usuario;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Usuario, Long> {

}
