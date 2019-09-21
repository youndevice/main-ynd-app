package com.youndevice.app.rest.controller;

import com.youndevice.app.dto.ErrorDTO;
import com.youndevice.app.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BaseApiController {

    private static final String VALIDATION_ERROR_CODE = "ValidationError";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleException(HttpMessageNotReadableException exception) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        responseDTO.setStatus(Boolean.FALSE);
        responseDTO.setMessage(messageSource.getMessage("invalid.request", null, "Please provide valid request", LocaleContextHolder.getLocale()));
        return responseDTO;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleException(MethodArgumentNotValidException exception) {
        System.out.println("Invalid Request. Message Source" + messageSource.getClass().getName());
        return createValidationError(exception);
    }

    private ResponseDTO<String> createValidationError(MethodArgumentNotValidException exception) {
        return fromBindingErrors(exception.getBindingResult());
    }

    public ResponseDTO<String> fromBindingErrors(Errors errors) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        ResponseDTO<String> errorResponseDTO = new ResponseDTO<String>();
        errorResponseDTO.setStatus(Boolean.FALSE);
        errorResponseDTO.setMessage("Invalid Request. Validation failed. " + errors.getErrorCount() + " error(s)");
        List<ErrorDTO> errorDTOs = errors.getAllErrors()
                .stream()
                .map(objectError -> new ErrorDTO(VALIDATION_ERROR_CODE, messageSource.getMessage(objectError.getDefaultMessage(), objectError.getArguments(), currentLocale)))
                .collect(Collectors.toList());
        errorResponseDTO.setErrors(errorDTOs);
        return errorResponseDTO;
    }
}