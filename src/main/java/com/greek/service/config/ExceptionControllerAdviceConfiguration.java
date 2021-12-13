/* AssentSoftware (C)2021 */
package com.greek.service.config;

import com.greek.service.exceptions.PersonAlreadyExistsDifferentOrganizationException;
import com.greek.service.exceptions.PersonAlreadyExistsException;
import com.greek.service.exceptions.PersonAlreadyExistsSameOrganizationException;
import com.gvt.core.response.ErrorResponse;
import com.gvt.rest.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdviceConfiguration {

    private static final String EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION =
            "error.person.already.exists.same.organization";
    private static final String EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION =
            "error.person.already.exists.different.organization";

    private final MessageSource messageSource;

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> personAlreadyExistsExceptionHandler(
            PersonAlreadyExistsException ex) {
        ErrorResponse error = null;

        try {
            if (ex instanceof PersonAlreadyExistsDifferentOrganizationException) {
                error =
                        new ErrorResponse(
                                messageSource.getMessage(
                                        EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION,
                                        null,
                                        LocaleContextHolder.getLocale()),
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION,
                                "25100");
            } else if (ex instanceof PersonAlreadyExistsSameOrganizationException) {
                error =
                        new ErrorResponse(
                                messageSource.getMessage(
                                        EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION,
                                        null,
                                        LocaleContextHolder.getLocale()),
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION,
                                "25200");
            }
        } catch (NoSuchMessageException e) {
            if (ex instanceof PersonAlreadyExistsDifferentOrganizationException) {
                error =
                        new ErrorResponse(
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION,
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_DIFFERENT_ORGANIZATION,
                                "25100");
            } else if (ex instanceof PersonAlreadyExistsSameOrganizationException) {
                error =
                        new ErrorResponse(
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION,
                                EXCEPTION_KEY_ERROR_PERSON_ALREADY_EXISTS_SAME_ORGANIZATION,
                                "25200");
            }
        }

        RequestUtils.addErrorResponseAdditionalInformation(error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
