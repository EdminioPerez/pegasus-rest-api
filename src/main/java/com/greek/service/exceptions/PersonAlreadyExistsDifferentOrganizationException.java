package com.greek.service.exceptions;

import com.greek.main.hibernate.model.Persona;

public class PersonAlreadyExistsDifferentOrganizationException extends PersonAlreadyExistsException {

	private static final long serialVersionUID = -3958607661971000988L;

	public PersonAlreadyExistsDifferentOrganizationException(Persona persona) {
		super(persona);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
