package com.greek.service.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.greek.commons.dto.v1.simple.SimpleDTO;
import com.greek.service.manager.SimpleDomainService;
import com.greek.service.mappers.SimpleDomainMapper;
import com.greek.service.rest.api.SimpleDomainRestAPI;

@RestController
public class SimpleDomainRestController implements SimpleDomainRestAPI {

	private SimpleDomainService simpleDomainService;
	private SimpleDomainMapper simpleDomainMapper;

	public SimpleDomainRestController(SimpleDomainService simpleDomainService, SimpleDomainMapper simpleDomainMapper) {
		this.simpleDomainService = simpleDomainService;
		this.simpleDomainMapper = simpleDomainMapper;
	}

	@Override
	public List<SimpleDTO> getBloodGroups() {
		return simpleDomainMapper.fromBloodGroupToDtos(simpleDomainService.findBloodGroups());
	}

	@Override
	public List<SimpleDTO> getSex() {
		return simpleDomainMapper.fromSexToDtos(simpleDomainService.findSex());
	}

	@Override
	public List<SimpleDTO> getIdentityDocumentsTypes() {
		return simpleDomainMapper
				.fromIdentityDocumentsTypesToDtos(simpleDomainService.findIdentityDocumentsTypeByCountry());
	}

	@Override
	public List<SimpleDTO> getCountries() {
		return simpleDomainMapper.fromGeographicLocationToDtos(simpleDomainService.findAllGeographicLocations());
	}

}
