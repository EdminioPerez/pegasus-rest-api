package com.greek.service.manager;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.greek.main.hibernate.model.CategoriaPersona;
import com.greek.main.hibernate.model.Organizacion;
import com.greek.main.hibernate.model.Persona;
import com.gvt.rest.crud.service.CrudService;

public interface PersonService extends CrudService<Persona> {

	public enum CategoriaPersonaEnum {
		ACREEDOR(5L), CLIENTE(1L), DOCTOR(9L), EMPLEADO(10L), MADRE(7L), PADRE(6L), PROVEEDOR(2L), REPRESENTANTE(4L),
		SEGURO(8L), VENDEDOR(3L);

		Long categoriaPersonaId;

		CategoriaPersonaEnum(Long categoriaPersonaId) {
			this.categoriaPersonaId = categoriaPersonaId;
		}

		public Long getIdCategoriaPersona() {
			return categoriaPersonaId;
		}
	}

	Persona saveWithOrganizationId(Persona entityToSave, CategoriaPersona categoriaPersona, Organizacion grupo);

	Page<Persona> findLike(String globalFilter, Pageable pageable);

	List<Persona> findByEMail(String emailPersona);

	void checkPersonIsPresentBehaviour(Persona persona, DataIntegrityViolationException e);

}
