/* AssentSoftware (C)2021 */
package com.greek.service.rest.controllers;

import com.greek.commons.dto.v1.simple.SimpleDto;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.SimpleDomainMapper;
import com.greek.service.rest.api.SimpleDomainRestApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SimpleDomainRestController implements SimpleDomainRestApi {

    private final SimpleDomainService simpleDomainService;
    private final SimpleDomainMapper simpleDomainMapper;

    @Override
    public List<SimpleDto> getBloodGroups() {
        return simpleDomainMapper.fromBloodGroupToDtos(simpleDomainService.findBloodGroups());
    }

    @Override
    public List<SimpleDto> getSex() {
        return simpleDomainMapper.fromSexToDtos(simpleDomainService.findSex());
    }

    @Override
    public List<SimpleDto> getIdentityDocumentsTypes() {
        return simpleDomainMapper.fromIdentityDocumentsTypesToDtos(
                simpleDomainService.findIdentityDocumentsTypeByCountry());
    }

    @Override
    public List<SimpleDto> getCountries() {
        return simpleDomainMapper.fromGeographicLocationToDtos(
                simpleDomainService.findAllGeographicLocations());
    }
}
