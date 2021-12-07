package com.greek.service.web.resources.disk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.greek.service.web.resources.ImagesResourceHandler;

@Component("imagesResourceHandler")
public class LocalImagesResourceHandler implements ImagesResourceHandler {

	private String resourcesDirectory;
	
	public LocalImagesResourceHandler(@Value("${app.resources.disk.directory}")
	 String resourcesDirectory) {
		this.resourcesDirectory = resourcesDirectory;
	}

	@Override
	public String getImage(String url) throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(resourcesDirectory, url));

		return Base64.getEncoder().encodeToString(fileContent);
	}

	@Override
	public String saveImage(MultipartFile file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("File can't be null");
		}

		var filePath = buildFilePath(file);

		try (var input = file.getInputStream()) {
			Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		return filePath.getFileName().toString();
	}

	private Path buildFilePath(MultipartFile file) throws IOException {
		var folder = Paths.get(resourcesDirectory);
		String filename = FilenameUtils.getBaseName(file.getOriginalFilename());
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		return Files.createTempFile(folder, filename + "-", "." + extension);
	}

}
