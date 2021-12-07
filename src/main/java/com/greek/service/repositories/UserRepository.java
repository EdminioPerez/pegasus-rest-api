/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Usuario, Long> {}
