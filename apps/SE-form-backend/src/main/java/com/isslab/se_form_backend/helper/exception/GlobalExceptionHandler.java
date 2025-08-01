package com.isslab.se_form_backend.helper.exception;

import com.isslab.se_form_backend.model.ErrorCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //所有錯誤訊息都由此回應
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("exception: ", ex);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorCodeResponse(ex.getClass().getSimpleName(), ex.getMessage()));
    }
}
