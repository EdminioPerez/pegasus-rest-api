package com.greek.service.manager;

import java.util.List;
import java.util.Locale;

import com.greek.commons.dto.v1.user.ClientDto;
import com.greek.commons.dto.v1.user.SystemUserDto;
import com.greek.main.hibernate.model.Organizacion;

public interface UserService {

	List<Organizacion> createUser(SystemUserDto usuarioSistema, ClientDto cliente, Long countryId, Locale locale);

	String[] builtRifsForOrganizations(SystemUserDto usuarioSistema, ClientDto cliente);

}
