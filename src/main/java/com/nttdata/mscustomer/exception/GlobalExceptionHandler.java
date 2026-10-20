package com.nttdata.mscustomer.exception;

import com.nttdata.mscustomer.generated.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ApiError> handleDuplicateCustomer(
            DuplicateCustomerException exception,
            ServerWebExchange exchange) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(toApiError(HttpStatus.CONFLICT, exception.getMessage(), exchange));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleInvalidCustomer(
            IllegalArgumentException exception,
            ServerWebExchange exchange) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(toApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exchange));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(
            ServerWebInputException exception,
            ServerWebExchange exchange) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(toApiError(HttpStatus.BAD_REQUEST,
                        "Invalid customer data",
                        exchange));
    }

    private ApiError toApiError(
            HttpStatus status,
            String message,
            ServerWebExchange exchange) {
        return new ApiError(
                status.value(),
                status.name(),
                message,
                exchange.getRequest().getPath().value());
    }
}
