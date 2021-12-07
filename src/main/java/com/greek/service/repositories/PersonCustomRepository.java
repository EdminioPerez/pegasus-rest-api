/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface PersonCustomRepository {

    Page<Persona> findPersonsLike(@Param("globalFilter") String globalFilter, Pageable p);
}
