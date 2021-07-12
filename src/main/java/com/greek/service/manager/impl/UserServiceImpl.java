package com.greek.service.manager.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greek.commons.dto.v1.user.ClientDTO;
import com.greek.commons.dto.v1.user.SystemUserDTO;
import com.greek.main.hibernate.model.CategoriaPersona;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.OpcionSino;
import com.greek.main.hibernate.model.Organizacion;
import com.greek.main.hibernate.model.Persona;
import com.greek.main.hibernate.model.Rol;
import com.greek.main.hibernate.model.RolUsuario;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.main.hibernate.model.UbicacionGeografica;
import com.greek.main.hibernate.model.Usuario;
import com.greek.main.hibernate.model.UsuarioOrganizacion;
import com.greek.service.manager.PersonService;
import com.greek.service.manager.PersonService.CategoriaPersonaEnum;
import com.greek.service.manager.UserService;
import com.greek.service.repositories.OrganizationRepository;
import com.greek.service.repositories.UserOrganizationRepository;
import com.greek.service.repositories.UserRepository;
import com.greek.service.repositories.UserRoleRepository;
import com.gvt.core.exceptions.LogicException;
import com.gvt.rest.context.i18n.Translator;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

	private PersonService personService;

	private UserRepository userRepository;
	private OrganizationRepository organizationRepository;
	private UserOrganizationRepository userOrganizationRepository;
	private UserRoleRepository userRolRepository;

	public UserServiceImpl(UserRepository userRepository, OrganizationRepository organizationRepository,
			UserOrganizationRepository userOrganizationRepository, UserRoleRepository userRolRepository,
			PersonService personService) {
		this.userRepository = userRepository;
		this.organizationRepository = organizationRepository;
		this.personService = personService;
		this.userOrganizationRepository = userOrganizationRepository;
		this.userRolRepository = userRolRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Organizacion> createUser(SystemUserDTO systemUser, ClientDTO client, Long countryId, Locale locale) {
		// Se configura el pais de registro y el idioma
		UbicacionGeografica pais = new UbicacionGeografica();
		pais.setId(countryId);

		// Se configuran primero las organizaciones
		Organizacion grupo = new Organizacion();
		Organizacion organizacion = new Organizacion();
		Organizacion sede = new Organizacion();

		List<Organizacion> retValue = new LinkedList<>();
		retValue.add(grupo);
		retValue.add(organizacion);
		retValue.add(sede);

		String[] rifs = builtRifsForOrganizations(systemUser, client);
		grupo.setRifOrganizacion(rifs[0]);
		organizacion.setRifOrganizacion(rifs[1]);
		sede.setRifOrganizacion(rifs[2]);

		OpcionSino opcionSinoByEsHabilitado = new OpcionSino();
		opcionSinoByEsHabilitado.setId(1l);

		OpcionSino opcionSinoByEsConfiguradoPapeleria = new OpcionSino();
		opcionSinoByEsConfiguradoPapeleria.setId(2l);

		grupo.setRazonOrganizacion(client.getRazonSocial() + "-G");
		grupo.setOpcionSinoByEsHabilitado(opcionSinoByEsHabilitado);
		grupo.setMaxOrganizacionesPorGrupo(5);
		grupo.setMaxSedesPorOrganizacion(3);
		organizacion.setRazonOrganizacion(client.getRazonSocial());
		organizacion.setOrganizacion(grupo);
		organizacion.setOpcionSinoByEsHabilitado(opcionSinoByEsHabilitado);
		sede.setRazonOrganizacion("Sede 1");
		sede.setOrganizacion(organizacion);
		sede.setOpcionSinoByEsHabilitado(opcionSinoByEsHabilitado);
		sede.setFormatoCabecera(Translator.toLocale("reporte.header.default", locale));
		sede.setFormatoPiePagina(Translator.toLocale("reporte.footer.default", locale));

		log.trace("Looking up for a organization with RIF:{}", organizacion.getRifOrganizacion());
		if (organizationRepository.findByRifOrganizacion(organizacion.getRifOrganizacion()) != null) {
			throw new LogicException("Organization already exists", "error.organization.already.exists");
		}

		organizationRepository.save(grupo);
		organizationRepository.save(organizacion);
		organizationRepository.save(sede);

		// Se configura el usuario
		Usuario usuario = new Usuario();
		usuario.setCodigoUsuario(systemUser.getEmail());
		usuario.setPasswordUsuario(systemUser.getContrasena());
		sede.setFormatoCabecera(Translator.toLocale("reporte.firma.default", locale));

		// Se habilita la cuenta
		OpcionSino opcionSinoByEsActiva = new OpcionSino();
		opcionSinoByEsActiva.setId(1L);
		usuario.setOpcionSinoByEsActiva(opcionSinoByEsActiva);

		OpcionSino opcionSinoByEsCuentaExpirada = new OpcionSino();
		opcionSinoByEsCuentaExpirada.setId(2L);
		usuario.setOpcionSinoByEsCuentaExpirada(opcionSinoByEsCuentaExpirada);

		OpcionSino opcionSinoByEsCuentaBloqueada = new OpcionSino();
		opcionSinoByEsCuentaBloqueada.setId(2L);
		usuario.setOpcionSinoByEsCuentaBloqueada(opcionSinoByEsCuentaBloqueada);

		OpcionSino opcionSinoByEsCredencialesExpiradas = new OpcionSino();
		opcionSinoByEsCredencialesExpiradas.setId(2L);
		usuario.setOpcionSinoByEsCredencialesExpiradas(opcionSinoByEsCredencialesExpiradas);

		userRepository.save(usuario);

		// Se configura al usuario como una persona de tipo doctor
		Persona doctor = new Persona();
		doctor.setApellidoPersona(systemUser.getApellidos());
		doctor.setNombrePersona(systemUser.getNombres());
		doctor.setCedulaPersona(client.getCedula());
		doctor.setEMailPersona(systemUser.getEmail());
		doctor.setTelefonoFijoPersona(systemUser.getTelefono());
		doctor.setDireccionPersona(client.getDireccion());
		doctor.setUbicacionGeograficaByIdPais(pais);

		CategoriaPersona categoriaPersona = new CategoriaPersona();
		categoriaPersona.setId(CategoriaPersonaEnum.DOCTOR.getIdCategoriaPersona());

		TipoDocumentoIdentificacion documentoIdentificacion = new TipoDocumentoIdentificacion();
		documentoIdentificacion.setId(2L);
		doctor.setTipoDocumentoIdentificacion(documentoIdentificacion);

		CodigoPostal codigoPostal = new CodigoPostal();
		codigoPostal.setId(1L);
		doctor.setCodigoPostal(codigoPostal);

		personService.saveWithOrganizationId(doctor, categoriaPersona, grupo);

		// Se enlaza al doctor con el usuario
		UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
		usuarioOrganizacion.setOrganizacionByIdOrganizacion(organizacion);
		usuarioOrganizacion.setOrganizacionByIdSede(sede);
		usuarioOrganizacion.setPersona(doctor);
		usuarioOrganizacion.setUsuario(usuario);

		userOrganizationRepository.save(usuarioOrganizacion);

		// Se enlaza el rol con el usuario
		Rol rol = new Rol();
		RolUsuario rolUsuario = new RolUsuario();

		if (systemUser.getIdProfesion() != null) {
			switch (systemUser.getIdProfesion().intValue()) {
			case 1:
				rol.setId(101L);
				break;
			case 53:
				rol.setId(104L);
				break;
			case 54:
				rol.setId(102L);
				break;
			case 55:
				rol.setId(100L);
				break;
			case 56:
				rol.setId(105L);
				break;
			case 57:
				rol.setId(103L);
				break;
			default:
				rol.setId(200L);
				break;
			}
		} else {
			rol.setId(50L);
		}

		rolUsuario.setUsuario(usuario);
		rolUsuario.setRol(rol);
		userRolRepository.save(rolUsuario);

//		// Añadir rol de permitir agregar paciente
//		rol = new Rol();
//		rolUsuario = new RolUsuario();
//
//		rol.setId(50L);
//		rolUsuario.setUsuario(usuario);
//		rolUsuario.setRol(rol);
//		userRolRepository.save(rolUsuario);

		return retValue;
	}

	@Override
	public String[] builtRifsForOrganizations(SystemUserDTO systemUser, ClientDTO client) {
		String rifGrupo;
		String rifOrganizacion;
		String rifSede;

		if (StringUtils.isNotBlank(client.getRif())) {
			rifGrupo = client.getRif() + "G";
			rifOrganizacion = client.getRif();
			rifSede = client.getRif() + "S";
		} else {
			if (StringUtils.startsWith(client.getCedula(), "Temp-")) {
				rifGrupo = StringUtils.left(systemUser.getIdValidation(), 15) + "G";
				rifOrganizacion = StringUtils.left(systemUser.getIdValidation(), 16);
				rifSede = StringUtils.left(systemUser.getIdValidation(), 15) + "S";
			} else {
				rifGrupo = StringUtils.left(client.getCedula(), 15) + "G";
				rifOrganizacion = client.getCedula();
				rifSede = StringUtils.left(client.getCedula(), 15) + "S";
			}
		}

		return new String[] { rifGrupo, rifOrganizacion, rifSede };
	}

}
