package com.greek.service.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greek.commons.dto.v1.simple.SimpleDto;
import com.gvt.swagger.annotations.ResponseOkSwagger;

@RequestMapping("/api/v1/simple")
public interface SimpleDomainRestApi {

	@GetMapping(value = "/blood-groups")
	@ResponseOkSwagger
	List<SimpleDto> getBloodGroups();

	@GetMapping(value = "/sex")
	@ResponseOkSwagger
	List<SimpleDto> getSex();

	@GetMapping(value = "/identity-documents-types")
	@ResponseOkSwagger
	List<SimpleDto> getIdentityDocumentsTypes();

	@GetMapping(value = "/countries")
	@ResponseOkSwagger
	List<SimpleDto> getCountries();

}