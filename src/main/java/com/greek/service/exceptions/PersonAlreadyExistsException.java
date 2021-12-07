package com.greek.service.exceptions;

import com.greek.main.hibernate.model.Persona;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PersonAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3958607661971000988L;

	private final Persona persona;

	@Override
	public String toString() {
		return "Trying to add a person that already exists in the app:" + persona.getCedulaPersona() + " --> countryId:"
				+ persona.getUbicacionGeograficaByIdPais().getId();
	}

}
