package com.app.medical_support.diagnosticexecution.exception.handler;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.exception.SpecimenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SpecimenExceptionHandler {

    @ExceptionHandler(SpecimenNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSpecimenNotFound(SpecimenNotFoundException ex) {
        log.warn("SpecimenNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}
