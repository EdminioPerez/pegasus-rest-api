package com.greek.service.rest.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greek.commons.dto.v1.simple.ImageDTO;
import com.greek.service.rest.api.ResourcesRestAPI;
import com.greek.service.web.resources.ImagesResourceHandler;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ResourcesUploaderController implements ResourcesRestAPI {

	private ImagesResourceHandler imagesResourceHandler;

	public ResourcesUploaderController(ImagesResourceHandler imagesResourceHandler) {
		this.imagesResourceHandler = imagesResourceHandler;
	}

	@Override
	public String downloadImage(String fileUrl) throws IOException {
		return imagesResourceHandler.getImage(fileUrl);
	}

	@Override
	public ImageDTO uploadImage(MultipartFile image) throws IOException {
		log.debug("Archivo recibido getName:{} getOriginalFilename:{}", image.getName(), image.getOriginalFilename());

		String filePath = imagesResourceHandler.saveImage(image);

		return new ImageDTO(filePath);
	}

}
