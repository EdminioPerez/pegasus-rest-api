/* AssentSoftware (C)2021 */
package com.greek.service.exceptions;

import com.greek.main.hibernate.model.Persona;

public class PersonAlreadyExistsSameOrganizationException extends PersonAlreadyExistsException {

    private static final long serialVersionUID = -3738264312318220233L;

    public PersonAlreadyExistsSameOrganizationException(Persona persona) {
        super(persona);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
