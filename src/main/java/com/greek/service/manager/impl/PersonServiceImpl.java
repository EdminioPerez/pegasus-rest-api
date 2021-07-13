package com.greek.service.manager.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greek.main.hibernate.model.CategoriaPersona;
import com.greek.main.hibernate.model.CodigoPostal;
import com.greek.main.hibernate.model.Organizacion;
import com.greek.main.hibernate.model.Persona;
import com.greek.main.hibernate.model.PersonaOrganizacion;
import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.greek.service.exceptions.PersonAlreadyExistsDifferentOrganizationException;
import com.greek.service.exceptions.PersonAlreadyExistsSameOrganizationException;
import com.greek.service.manager.OrganizationService;
import com.greek.service.manager.OrganizationService.TipoDocumentoEnum;
import com.greek.service.manager.PersonService;
import com.greek.service.repositories.IdentityDocumentTypeRepository;
import com.greek.service.repositories.PersonOrganizationRepository;
import com.greek.service.repositories.PersonRepository;
import com.greek.service.repositories.PostalCodesRepository;
import com.greek.service.utils.AuthenticationUtils;
import com.greek.service.utils.DatabaseConstraintUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PersonServiceImpl implements PersonService {

	private OrganizationService organizationService;

	private PersonRepository personRepository;
	private PersonOrganizationRepository personOrganizationRepository;
	private IdentityDocumentTypeRepository identityDocumentTypeRepository;
	private PostalCodesRepository postalCodesRepository;

	public PersonServiceImpl(PersonRepository personRepository, OrganizationService organizationService,
			PersonOrganizationRepository personOrganizationRepository,

			IdentityDocumentTypeRepository identityDocumentTypeRepository,
			PostalCodesRepository postalCodesRepository) {
		this.personRepository = personRepository;
		this.organizationService = organizationService;
		this.personOrganizationRepository = personOrganizationRepository;
		this.identityDocumentTypeRepository = identityDocumentTypeRepository;
		this.postalCodesRepository = postalCodesRepository;
	}

	@Override
	public Optional<Persona> findById(Long id) {
		return personRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Persona save(Persona entityToSave) {
		log.debug("Value of entity for saving:{}", entityToSave);

		preValidationsOnPersonUpdate(entityToSave);

		CategoriaPersona categoriaPersona = new CategoriaPersona();
		categoriaPersona.setId(CategoriaPersonaEnum.CLIENTE.getIdCategoriaPersona());

		entityToSave.setCodigoPersona(buildCode(categoriaPersona, AuthenticationUtils.getCurrentGroup()));

		Persona personaSaved = personRepository.save(entityToSave);

		PersonaOrganizacion personaOrganizacion = new PersonaOrganizacion();
		personaOrganizacion.setCategoriaPersona(categoriaPersona);
		personaOrganizacion.setPersona(personaSaved);
		personaOrganizacion.setOrganizacion(AuthenticationUtils.getCurrentGroup());
		personOrganizationRepository.save(personaOrganizacion);

		return personaSaved;
	}

	@Override
	@Transactional(readOnly = false)
	public Persona saveWithOrganizationId(Persona entityToSave, CategoriaPersona personCategory, Organizacion group) {
		preValidationsOnPersonUpdate(entityToSave);

		log.debug("Valor del grupo:{}", group);

		entityToSave.setCodigoPersona(buildCode(personCategory, group));

		Persona personaSaved = personRepository.save(entityToSave);

		PersonaOrganizacion personaOrganizacion = new PersonaOrganizacion();
		personaOrganizacion.setCategoriaPersona(personCategory);
		personaOrganizacion.setPersona(personaSaved);
		personaOrganizacion.setOrganizacion(group);
		personOrganizationRepository.save(personaOrganizacion);

		return personaSaved;
	}

	@Override
	@Transactional(readOnly = false)
//	@PreAuthorize("#entityToMerge.organizacion.id == authentication.details.rootCenterId")
	public Persona update(Persona entityToMerge) {
		preValidationsOnPersonUpdate(entityToMerge);

		// TODO aqui hay que chequear que la organizacion del token es igual a un
		// registro en persona_organizacion

		return personRepository.save(entityToMerge);
	}

	@Override
	public Page<Persona> findLike(String globalFilter, Pageable pageable) {
		return personRepository.findPersonsLike(globalFilter, pageable);
	}

	@Override
	public List<Persona> findByEMail(String emailPersona) {
		return personRepository.findByEMailPersona(emailPersona);
	}

	@Override
	public void checkPersonIsPresentBehaviour(Persona persona, DataIntegrityViolationException e) {
		Optional<Entry<String, String>> constraint = DatabaseConstraintUtils.getConstraintName(e);

		if (constraint.isPresent() && constraint.get().getValue().equalsIgnoreCase("exception.person.already.exists")) {
			log.debug("Si, es una exception del tipo que ya existe, que hacemos?");

			preValidationsOnPersonUpdate(persona);

			Optional<Long> personId = personRepository.findByGlobalConstraint(
					persona.getTipoDocumentoIdentificacion().getId(), persona.getCedulaPersona(),
					persona.getUbicacionGeograficaByIdPais().getId());

			if (personId.isPresent()) {
				personOrganizationRepository.findByPersonaId(personId.get()).ifPresentOrElse(value -> {
					throw new PersonAlreadyExistsSameOrganizationException(persona);
				}, () -> {
					throw new PersonAlreadyExistsDifferentOrganizationException(persona);
				});
			}
		} else {
			throw e;
		}
	}

	private void preValidationsOnPersonUpdate(Persona entityToSave) {
		if (StringUtils.isBlank(entityToSave.getTelefonoFijoPersona())) {
			entityToSave.setTelefonoFijoPersona("N/A");
		}

		TipoDocumentoIdentificacion identityDocumentType = identityDocumentTypeRepository
				.findById(entityToSave.getTipoDocumentoIdentificacion().getId()).orElseThrow(
						() -> new DataIntegrityViolationException("fk_id_tipo_documento_identificacion_from_persona"));

		CodigoPostal postalCode = postalCodesRepository.findById(entityToSave.getCodigoPostal().getId())
				.orElseThrow(() -> new DataIntegrityViolationException("fk_id_codigo_postal_from_persona"));

		entityToSave.setCodigoPostal(postalCode);
		entityToSave.setTipoDocumentoIdentificacion(identityDocumentType);
		entityToSave.setUbicacionGeograficaByIdPais(postalCode.getUbicacionGeografica());
	}

	private String buildCode(CategoriaPersona categoriaPersona, Organizacion organizacion) {
		if (categoriaPersona.getId().longValue() == CategoriaPersonaEnum.CLIENTE.getIdCategoriaPersona().longValue()) {
			return LocalDate.now().getYear() + StringUtils.leftPad(
					organizationService.getNextCodeNumber(organizacion.getId(), TipoDocumentoEnum.HISTORIA).toString(),
					4, '0');
		} else if (categoriaPersona.getId().longValue() == CategoriaPersonaEnum.DOCTOR.getIdCategoriaPersona()
				.longValue()
				|| categoriaPersona.getId().longValue() == CategoriaPersonaEnum.EMPLEADO.getIdCategoriaPersona()
						.longValue()) {
			return "EMPL" + StringUtils.leftPad(
					organizationService.getNextCodeNumber(organizacion.getId(), TipoDocumentoEnum.EMPLEADO).toString(),
					4, '0');
		} else if (categoriaPersona.getId().longValue() == CategoriaPersonaEnum.SEGURO.getIdCategoriaPersona()
				.longValue()
				|| categoriaPersona.getId().longValue() == CategoriaPersonaEnum.ACREEDOR.getIdCategoriaPersona()
						.longValue()) {
			return "ACRE" + StringUtils.leftPad(
					organizationService.getNextCodeNumber(organizacion.getId(), TipoDocumentoEnum.ACREEDOR).toString(),
					4, '0');
		}

		return null;
	}

}
