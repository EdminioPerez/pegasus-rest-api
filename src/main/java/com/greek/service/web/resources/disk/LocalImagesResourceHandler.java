package com.greek.service.web.resources.disk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

	@Value("${app.resources.disk.directory}")
	private String resourcesDirectory;

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

		Path folder = Paths.get(resourcesDirectory);
		String filename = FilenameUtils.getBaseName(file.getOriginalFilename());
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		Path filePath = Files.createTempFile(folder, filename + "-", "." + extension);

		try (InputStream input = file.getInputStream()) {
			Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		return filePath.getFileName().toString();
	}

}
