package com.greek.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.Sexo;

@Repository
public interface SexRepository extends JpaRepository<Sexo, Long> {

}
