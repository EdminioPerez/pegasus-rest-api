package com.greek.service.domain.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.greek.main.hibernate.model.Persona;

@Projection(name = "customPerson", types = { Persona.class })
public interface CustomPerson {

	@Value("#{target.id}")
	long getId();

}
