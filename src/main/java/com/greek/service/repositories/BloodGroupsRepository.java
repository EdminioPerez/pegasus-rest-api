/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.TipoSangre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodGroupsRepository extends JpaRepository<TipoSangre, Long> {}
