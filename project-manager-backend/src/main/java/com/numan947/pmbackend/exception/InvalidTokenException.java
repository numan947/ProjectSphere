package com.numan947.pmbackend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidTokenException extends RuntimeException {
    private final String msg;
}