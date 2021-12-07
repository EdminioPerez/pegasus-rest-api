/* AssentSoftware (C)2021 */
package com.greek.service.manager.impl;

import com.greek.main.hibernate.model.Organizacion;
import com.greek.service.manager.OrganizationService;
import com.greek.service.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY)
    public Long getNextCodeNumber(Long organizacionId, TipoDocumentoEnum tipoDocumentoEnum) {
        Long retValue = null;

        Organizacion organizacion = organizationRepository.findOrganizationForWrite(organizacionId);

        retValue = organizacion.getId();

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
