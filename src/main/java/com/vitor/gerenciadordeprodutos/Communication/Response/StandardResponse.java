package com.vitor.gerenciadordeprodutos.Communication.Response;

import org.springframework.http.HttpStatus;

public record StandardResponse<T>(
        T data,
        Boolean success,
        String message,
        Object errors,
        HttpStatus httpStatus
) { }
