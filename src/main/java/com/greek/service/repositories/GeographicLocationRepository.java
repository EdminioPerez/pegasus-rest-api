/* AssentSoftware (C)2021 */
package com.greek.service.repositories;

import com.greek.main.hibernate.model.UbicacionGeografica;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeographicLocationRepository extends JpaRepository<UbicacionGeografica, Long> {

    Optional<UbicacionGeografica> findByCodigoUbicacionGeografica(String code);

    List<UbicacionGeografica> findByUbicacionGeograficaIsNull();
}
