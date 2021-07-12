package com.greek.service.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.greek.commons.dto.v1.simple.SimpleDTO;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.SimpleDomainMapper;
import com.greek.service.rest.api.PostalCodesRestAPI;

@RestController
public class PostalCodesRestController implements PostalCodesRestAPI {

	private SimpleDomainService simpleDomainService;
	private SimpleDomainMapper simpleDomainMapper;

	public PostalCodesRestController(SimpleDomainService simpleDomainService, SimpleDomainMapper simpleDomainMapper) {
		this.simpleDomainService = simpleDomainService;
		this.simpleDomainMapper = simpleDomainMapper;
	}

	@Override
	public List<SimpleDTO> getProvinces() {
		return simpleDomainMapper.fromProvinceToDtos(simpleDomainService.findProvinces());
	}

	@Override
	public List<SimpleDTO> getMunicipalities(Long provinceId) {
		return simpleDomainMapper.fromMunicipalityToDtos(simpleDomainService.findMunicipalities(provinceId));
	}

	@Override
	public List<SimpleDTO> getPostalCodes(Long provinceId, Long municipalityId) {
		return simpleDomainMapper
				.fromPostalCodesToDtos(simpleDomainService.findPostalCodes(provinceId, municipalityId));
	}

}
