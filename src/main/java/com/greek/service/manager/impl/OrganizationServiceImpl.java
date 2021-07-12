package com.greek.service.manager.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.greek.service.manager.OrganizationService;
import com.greek.service.repositories.OrganizationRepository;
import com.greek.main.hibernate.model.Organizacion;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationRepository organizationRepository;

	public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	public Long getNextCodeNumber(Long organizacionId, TipoDocumentoEnum tipoDocumentoEnum) {
		Long retValue = null;

		Organizacion organizacion = organizationRepository.findOrganizationForWrite(organizacionId);
		
		retValue= organizacion.getId();

//		if (tipoDocumentoEnum == TipoDocumentoEnum.EMPLEADO) {
//			retValue = organizacion.getNextNroPedido();
//
//			if (retValue == null) {
//				organizacion.setNextNroPedido(2L);
//				retValue = 1L;
//			} else {
//				organizacion.setNextNroPedido(retValue.longValue() + 1);
//			}
//		}

		return retValue;
	}

}
