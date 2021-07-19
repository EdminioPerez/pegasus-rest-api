package com.greek.service.rest.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.greek.commons.dto.v1.simple.ValidationAnnotatedDTO;
import com.gvt.core.exceptions.LogicException;
import com.gvt.core.response.FieldErrorResource;
import com.gvt.rest.utils.dtos.KeyValueResponseDTO;

@RestController
@RequestMapping("/api/v1/exception-handling")
public class ExceptionHandlingRestController {

	@GetMapping("/logic-exception")
	public ResponseEntity<String> logicException() {
		throw new LogicException("Prueba de exception", "error.exception.logic-exception");
	}

	@GetMapping("/logic-exception-fields")
	public ResponseEntity<String> logicExceptionWithFields() {
		FieldErrorResource fer = new FieldErrorResource("nombre", "Nombre inválido", "error.invalid.name");

		throw new LogicException("Prueba de exception", "error.exception.logic-exception", Arrays.asList(fer));
	}

	@GetMapping("/logic-exception-translated")
	public ResponseEntity<String> logicExceptionTranslated() {
		throw new LogicException("Prueba de exception", "error.exception.logic-exception.translated");
	}

	@GetMapping("/missing-param")
	public ResponseEntity<KeyValueResponseDTO> missingParam(@RequestParam(name = "key") String missingParam,
			@RequestParam(name = "other_param") String otherParam) {
		return new ResponseEntity<>(new KeyValueResponseDTO(missingParam), HttpStatus.OK);
	}

	@PostMapping("/validate-dto")
	public ResponseEntity<KeyValueResponseDTO> validateDTO(@Valid @RequestBody ValidationAnnotatedDTO annotatedDTO) {
		return new ResponseEntity<>(new KeyValueResponseDTO("Harold"), HttpStatus.OK);
	}

	@PostMapping("/exceed-file-size")
	public ResponseEntity<KeyValueResponseDTO> validateFileSizeExceeded(@RequestPart("file") MultipartFile file) {
		throw new MultipartException("This excedede size");
	}

	@GetMapping("/single-status-error-response")
	public ResponseEntity<String> singleStatusErrorResponse() {
		return new ResponseEntity<>("503", HttpStatus.SERVICE_UNAVAILABLE);
	}

}
