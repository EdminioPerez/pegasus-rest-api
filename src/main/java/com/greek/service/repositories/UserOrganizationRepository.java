/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.UsuarioOrganizacion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrganizationRepository
        extends PagingAndSortingRepository<UsuarioOrganizacion, Long> {}
