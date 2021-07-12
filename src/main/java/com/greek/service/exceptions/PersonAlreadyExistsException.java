package com.greek.service.exceptions;

import com.greek.main.hibernate.model.Persona;

public class PersonAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3958607661971000988L;

	private final Persona persona;

	public PersonAlreadyExistsException(Persona persona) {
		this.persona = persona;
	}

	public Persona getPersona() {
		return persona;
	}

	@Override
	public String toString() {
		return "Trying to add a person that already exists in the app:" + persona.getCedulaPersona() + " --> countryId:"
				+ persona.getUbicacionGeograficaByIdPais().getId();
	}

}
