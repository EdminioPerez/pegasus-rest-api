package com.greek.service.rest.api;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.greek.commons.dto.v1.simple.ImageDto;
import com.gvt.swagger.annotations.ResponseOkSwagger;

@RequestMapping("/api/v1/resources")
public interface ResourcesRestApi {

	@GetMapping(value = "/image")
	@ResponseOkSwagger
	String downloadImage(@RequestParam("url") String fileUrl) throws IOException;

	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ImageDto uploadImage(@RequestPart(value = "image") MultipartFile image) throws IOException;

}