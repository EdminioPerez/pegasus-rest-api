package com.greek.service.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.greek.commons.dto.v1.simple.SimpleDto;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.SimpleDomainMapper;
import com.greek.service.rest.api.PostalCodesRestApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostalCodesRestController implements PostalCodesRestApi {

	private final SimpleDomainService simpleDomainService;
	private final SimpleDomainMapper simpleDomainMapper;

	@Override
	public List<SimpleDto> getProvinces() {
		return simpleDomainMapper.fromProvinceToDtos(simpleDomainService.findProvinces());
	}

	@Override
	public List<SimpleDto> getMunicipalities(Long provinceId) {
		return simpleDomainMapper.fromMunicipalityToDtos(simpleDomainService.findMunicipalities(provinceId));
	}

	@Override
	public List<SimpleDto> getPostalCodes(Long provinceId, Long municipalityId) {
		return simpleDomainMapper
				.fromPostalCodesToDtos(simpleDomainService.findPostalCodes(provinceId, municipalityId));
	}

}
