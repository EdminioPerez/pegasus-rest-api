package com.greek.service.web.resources;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImagesResourceHandler {

	String getImage(String url) throws IOException;

	String saveImage(MultipartFile file) throws IOException;

}