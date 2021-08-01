package com.greek.service.config;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greek.service.exceptions.PersonAlreadyExistsDifferentOrganizationException;
import com.greek.service.exceptions.PersonAlreadyExistsException;
import com.greek.service.exceptions.PersonAlreadyExistsSameOrganizationException;
import com.greek.service.utils.DatabaseConstraintUtils;
import com.gvt.core.response.ErrorResponse;
import com.gvt.data.controllers.DefaultJPAExceptionHandlerController;
import com.gvt.rest.utils.RequestUtils;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
public class ExceptionControllerAdviceConfiguration {

	private static final String EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION = "error.person.already.exists.same.organization";
	private static final String EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION = "error.person.already.exists.different.organization";

	private MessageSource messageSource;

	public ExceptionControllerAdviceConfiguration(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(PersonAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> personAlreadyExistsExceptionHandler(PersonAlreadyExistsException ex) {
		ErrorResponse error = null;

		try {
			if (ex instanceof PersonAlreadyExistsDifferentOrganizationException) {
				error = new ErrorResponse(
						messageSource.getMessage(EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION, null,
								LocaleContextHolder.getLocale()),
						EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION, "25100");
			} else if (ex instanceof PersonAlreadyExistsSameOrganizationException) {
				error = new ErrorResponse(
						messageSource.getMessage(EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION, null,
								LocaleContextHolder.getLocale()),
						EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION, "25200");
			}
		} catch (NoSuchMessageException e) {
			if (ex instanceof PersonAlreadyExistsDifferentOrganizationException) {
				error = new ErrorResponse(EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION,
						EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION, "25100");
			} else if (ex instanceof PersonAlreadyExistsSameOrganizationException) {
				error = new ErrorResponse(EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION,
						EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION, "25200");
			}
		}

		HttpServletRequest currentRequest = RequestUtils.getCurrentHttpRequest();
		if (currentRequest != null) {
			error.setPath(currentRequest.getRequestURI());
			error.setMethod(currentRequest.getMethod());
		}

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
		log.error(DefaultJPAExceptionHandlerController.BUILDING_ERROR_RESPONSE, ex);

		ErrorResponse error = null;

		String rootMsg = ExceptionUtils.getRootCause(ex).getMessage();

		if (StringUtils.isNotBlank(rootMsg)) {
			Optional<Map.Entry<String, String>> entry = DatabaseConstraintUtils.getConstraintName(ex);

			if (entry.isPresent()) {
				try {
					error = new ErrorResponse(
							messageSource.getMessage(entry.get().getValue(), null, LocaleContextHolder.getLocale()),
							entry.get().getValue(), "8000");
				} catch (NoSuchMessageException e) {
					error = new ErrorResponse(rootMsg, entry.get().getValue(), "8000");
				}
			} else {
				error = new ErrorResponse(rootMsg, "exception.constraint.translation.undefined", "8000");
			}
		} else {
			new ErrorResponse("Data integrity with unknown constraint", "exception.constraint.undefined", "8000");
		}

		HttpServletRequest currentRequest = RequestUtils.getCurrentHttpRequest();
		if (currentRequest != null) {
			error.setPath(currentRequest.getRequestURI());
			error.setMethod(currentRequest.getMethod());
		}

		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
