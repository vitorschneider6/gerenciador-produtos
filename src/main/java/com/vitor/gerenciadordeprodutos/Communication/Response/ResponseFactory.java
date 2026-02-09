package com.vitor.gerenciadordeprodutos.Communication.Response;

import org.springframework.http.HttpStatus;

public class ResponseFactory {

    public static <T> StandardResponse<T> ok(T data, String message) {
        return new StandardResponse<>(
                data,
                true,
                message,
                null,
                HttpStatus.OK
        );
    }

    public static <T> StandardResponse<T> error(
            String message,
            Object errors,
            HttpStatus status
    ) {
        return new StandardResponse<>(
                null,
                false,
                message,
                errors,
                status
        );
    }
}

