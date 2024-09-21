package com.numan947.pmbackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

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
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
}