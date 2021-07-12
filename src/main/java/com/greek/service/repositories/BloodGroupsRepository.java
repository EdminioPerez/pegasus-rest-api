package com.greek.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greek.main.hibernate.model.TipoSangre;

@Repository
public interface BloodGroupsRepository extends JpaRepository<TipoSangre, Long> {

}
