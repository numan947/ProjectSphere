package com.numan947.pmbackend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OperationNotPermittedException is a custom exception that is thrown when an operation is not permitted.
 * It extends RuntimeException and includes an additional message field.
 *
 * Fields:
 * - msg: A message describing why the operation is not permitted.
 *
 * Annotations:
 * - @Data: Lombok annotation to generate getters, setters, toString, equals, and hashCode methods.
 * - @EqualsAndHashCode(callSuper = true): Lombok annotation to include the superclass fields in the equals and hashCode methods.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationNotPermittedException extends RuntimeException {
    private final String msg;
}