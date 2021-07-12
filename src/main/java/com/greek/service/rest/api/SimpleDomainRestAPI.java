package com.greek.service.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greek.commons.dto.v1.simple.SimpleDTO;
import com.gvt.swagger.annotations.ResponseOkSwagger;

@RequestMapping("/api/v1/simple")
public interface SimpleDomainRestAPI {

	@GetMapping(value = "/blood-groups")
	@ResponseOkSwagger
	List<SimpleDTO> getBloodGroups();

	@GetMapping(value = "/sex")
	@ResponseOkSwagger
	List<SimpleDTO> getSex();

	@GetMapping(value = "/identity-documents-types")
	@ResponseOkSwagger
	List<SimpleDTO> getIdentityDocumentsTypes();

	@GetMapping(value = "/countries")
	@ResponseOkSwagger
	List<SimpleDTO> getCountries();

}