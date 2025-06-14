package com.eduardo.oficina.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
        // Exceções genéricas do tipo illegalArgumentsException
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                        HttpServletRequest request) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad request",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // Exceções quando algum item não é encontrado
        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<ErrorResponse> handleNoSuchElementException(
                        NoSuchElementException ex, HttpServletRequest request) {
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // tratar erros de autenticação
        @ExceptionHandler(SecurityException.class)
        public ResponseEntity<ErrorResponse> handleSecurityException(
                        SecurityException ex, HttpServletRequest request) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.UNAUTHORIZED.value(),
                                "Unauthorized",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // tratar confitos em requisições
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorResponse> handleIllegalStateException(
                        IllegalStateException ex, HttpServletRequest request) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                "Conflict",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Exceções genéricas não tratadas
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(
                        Exception ex, HttpServletRequest request) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                                "Internal Server Error",
                                "Ocorreu um erro inesperado. Tente novamente.",
                                request.getRequestURI());

                // Você pode também logar esse erro no console ou em arquivos aqui
                ex.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {

                StringBuilder message = new StringBuilder("Erros de validação: ");

                for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                        message.append(String.format("[%s: %s] ", fieldError.getField(),
                                        fieldError.getDefaultMessage()));
                }

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                message.toString(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(EstoqueInsuficienteException.class)
        public ResponseEntity<ErrorResponse> handleEstoqueInsuficienteException(
                        EstoqueInsuficienteException ex, HttpServletRequest request) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                "Unprocessable Entity",
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
}
