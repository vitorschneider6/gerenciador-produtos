package com.vitor.gerenciadordeprodutos.Communication.Exception;

import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardResponse<Void>> handleNotFound(
            EntityNotFoundException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseFactory.error(
                        ex.getMessage(),
                        null,
                        HttpStatus.NOT_FOUND
                )
        );
    }
}

