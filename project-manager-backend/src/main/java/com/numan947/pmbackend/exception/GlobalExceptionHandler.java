package com.numan947.pmbackend.exception;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(LockedException exp)
    {
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

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(DisabledException exp)
    {
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(BadCredentialsException exp)
    {
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(EntityNotFoundException exp)
    {
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

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(MessagingException exp)
    {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(MethodArgumentNotValidException exp)
    {
        Set<String>errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error->errors.add(error.getDefaultMessage()));
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

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponseDTO>handle(OperationNotPermittedException exp)
    {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO>handle(Exception exp)
    {
        //log the exception
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