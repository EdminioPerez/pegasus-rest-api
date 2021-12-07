/* AssentSoftware (C)2021 */
package com.greek.service.domain.projections;

import com.greek.main.hibernate.model.Persona;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "customPerson",
        types = {Persona.class})
public interface CustomPerson {

    @Value("#{target.id}")
    long getId();
}
