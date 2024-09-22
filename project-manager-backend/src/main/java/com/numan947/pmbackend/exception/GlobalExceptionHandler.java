package com.numan947.pmbackend.exception;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashSet;
import java.util.Set;

/**
 * GlobalExceptionHandler handles various exceptions thrown by the application and returns appropriate
 * HTTP responses with error details encapsulated in ExceptionResponseDTO.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles LockedException and returns a response with HTTP status 401 (UNAUTHORIZED).
     *
     * @param exp the LockedException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(LockedException exp) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ExceptionResponseDTO(
                        BusinessErrorCodes.ACCOUNT_LOCKED.getCode(),
                        BusinessErrorCodes.ACCOUNT_LOCKED.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    /**
     * Handles DisabledException and returns a response with HTTP status 401 (UNAUTHORIZED).
     *
     * @param exp the DisabledException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(DisabledException exp) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ExceptionResponseDTO(
                        BusinessErrorCodes.ACCOUNT_DISABLED.getCode(),
                        BusinessErrorCodes.ACCOUNT_DISABLED.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    /**
     * Handles BadCredentialsException and returns a response with HTTP status 401 (UNAUTHORIZED).
     *
     * @param exp the BadCredentialsException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(BadCredentialsException exp) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ExceptionResponseDTO(
                        BusinessErrorCodes.BAD_CREDENTIALS.getCode(),
                        BusinessErrorCodes.BAD_CREDENTIALS.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    /**
     * Handles EntityNotFoundException and returns a response with HTTP status 404 (NOT_FOUND).
     *
     * @param exp the EntityNotFoundException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(EntityNotFoundException exp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionResponseDTO(
                        BusinessErrorCodes.ENTITY_NOT_FOUND.getCode(),
                        BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription(),
                        "Entity not found",
                        null,
                        null
                )
        );
    }

    /**
     * Handles MessagingException and returns a response with HTTP status 500 (INTERNAL_SERVER_ERROR).
     *
     * @param exp the MessagingException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(MessagingException exp) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionResponseDTO(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    /**
     * Handles MethodArgumentNotValidException and returns a response with HTTP status 400 (BAD_REQUEST).
     *
     * @param exp the MethodArgumentNotValidException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(
                        null,
                        null,
                        null,
                        errors,
                        null
                )
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(HandlerMethodValidationException exp) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(HttpRequestMethodNotSupportedException exp) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ExceptionResponseDTO(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(MissingServletRequestParameterException exp) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }



    /**
     * Handles OperationNotPermittedException and returns a response with HTTP status 403 (FORBIDDEN).
     *
     * @param exp the OperationNotPermittedException
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponseDTO> handle(OperationNotPermittedException exp) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionResponseDTO(
                        BusinessErrorCodes.OPERATION_NOT_PERMITTED.getCode(),
                        BusinessErrorCodes.OPERATION_NOT_PERMITTED.getDescription(),
                        exp.getMsg(),
                        null,
                        null
                )
        );
    }

    /**
     * Handles generic Exception and returns a response with HTTP status 500 (INTERNAL_SERVER_ERROR).
     *
     * @param exp the Exception
     * @return ResponseEntity with ExceptionResponseDTO
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handle(Exception exp) {
        // log the exception
        exp.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionResponseDTO(
                        null,
                        "Internal Server Error, contact support",
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }
}