package com.greek.service.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greek.commons.dto.v1.simple.SimpleDto;
import com.gvt.swagger.annotations.ResponseOkSwagger;

@RequestMapping("/api/v1")
public interface PostalCodesRestApi {

	@GetMapping(value = "/provinces")
	@ResponseOkSwagger
	List<SimpleDto> getProvinces();

	@GetMapping(value = "/provinces/{province_id}/municipalities")
	@ResponseOkSwagger
	List<SimpleDto> getMunicipalities(@PathVariable("province_id") Long provinceId);

	@GetMapping(value = "/provinces/{province_id}/municipalities/{municipality_id}/postal-codes")
	@ResponseOkSwagger
	List<SimpleDto> getPostalCodes(@PathVariable("province_id") Long provinceId,
			@PathVariable("municipality_id") Long municipalityId);

}