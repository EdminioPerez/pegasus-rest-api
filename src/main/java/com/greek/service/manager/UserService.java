package com.greek.service.manager;

import java.util.List;
import java.util.Locale;

import com.greek.commons.dto.v1.user.ClientDTO;
import com.greek.commons.dto.v1.user.SystemUserDTO;
import com.greek.main.hibernate.model.Organizacion;

public interface UserService {

	List<Organizacion> createUser(SystemUserDTO usuarioSistema, ClientDTO cliente, Long countryId, Locale locale);

	String[] builtRifsForOrganizations(SystemUserDTO usuarioSistema, ClientDTO cliente);

}
