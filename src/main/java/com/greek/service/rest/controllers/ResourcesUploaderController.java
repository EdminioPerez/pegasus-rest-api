/* AssentSoftware (C)2021 */
package com.greek.service.rest.controllers;

import com.greek.commons.dto.v1.simple.ImageDto;
import com.greek.service.rest.api.ResourcesRestApi;
import com.greek.service.web.resources.ImagesResourceHandler;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ResourcesUploaderController implements ResourcesRestApi {

    private final ImagesResourceHandler imagesResourceHandler;

    @Override
    public String downloadImage(String fileUrl) throws IOException {
        return imagesResourceHandler.getImage(fileUrl);
    }

    @Override
    public ImageDto uploadImage(MultipartFile image) throws IOException {
        log.debug(
                "Archivo recibido getName:{} getOriginalFilename:{}",
                image.getName(),
                image.getOriginalFilename());

        String filePath = imagesResourceHandler.saveImage(image);

        return new ImageDto(filePath);
    }
}
