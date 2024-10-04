package com.numan947.pmbackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
/**
 * BusinessErrorCodes is an enumeration that defines various business error codes and their associated HTTP status.
 *
 * Fields:
 * - code: The unique code representing the error.
 * - description: A brief description of the error.
 * - httpStatus: The HTTP status associated with the error.
 *
 * Enum Constants:
 * - NO_CODE: Represents a "Not Implemented" error with HTTP status 501 (NOT_IMPLEMENTED).
 * - BAD_CREDENTIALS: Represents an "Incorrect credentials" error with HTTP status 400 (BAD_REQUEST).
 * - NEW_PASSWORD_DOES_NOT_MATCH: Represents a "New password does not match" error with HTTP status 400 (BAD_REQUEST).
 * - ACCOUNT_LOCKED: Represents an "Account is locked" error with HTTP status 423 (LOCKED).
 * - ACCOUNT_DISABLED: Represents an "Account is disabled" error with HTTP status 403 (FORBIDDEN).
 * - OPERATION_NOT_PERMITTED: Represents an "Operation not permitted" error with HTTP status 403 (FORBIDDEN).
 * - ENTITY_NOT_FOUND: Represents an "Entity not found" error with HTTP status 404 (NOT_FOUND).
 *
 * Annotations:
 * - @RequiredArgsConstructor: Lombok annotation to generate a constructor with required arguments.
 * - @Getter: Lombok annotation to generate getter methods for all fields.
 */


@RequiredArgsConstructor
@Getter
public enum BusinessErrorCodes {
    NO_CODE(0, "Not Implemented", NOT_IMPLEMENTED),
    BAD_CREDENTIALS(300, "Incorrect credentials: Login email/password is incorrect", BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301, "New password does not match", BAD_REQUEST),
    ACCOUNT_LOCKED(302, "Account is locked", LOCKED),
    ACCOUNT_DISABLED(303, "Account is disabled", FORBIDDEN),
    OPERATION_NOT_PERMITTED(401, "Operation not permitted", FORBIDDEN),
    ENTITY_NOT_FOUND(404, "Entity not found", NOT_FOUND),
    JWT_EXPIRED(405, "JWT token has expired", UNAUTHORIZED),
    TOKEN_INVALID(406, "Provided token is not valid", UNAUTHORIZED),
    ACCOUNT_EXISTS(305, "Account already exists", BAD_REQUEST),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
}