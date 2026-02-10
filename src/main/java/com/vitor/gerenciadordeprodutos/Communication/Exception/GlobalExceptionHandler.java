package com.vitor.gerenciadordeprodutos.Communication.Exception;

import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Exceptions.BusinessException;
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardResponse<Void>> handleBusinessError(
            BusinessException ex
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseFactory.error(
                        ex.getMessage(),
                        null,
                        HttpStatus.BAD_REQUEST
                )
        );
    }
}

