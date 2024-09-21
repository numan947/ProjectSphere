package com.numan947.pmbackend.security.token;

public enum TokenTypes {
    VERIFICATION,
    ACTIVATION,
    PASSWORD_RESET;

    //convert enum to string
    public String toString() {
        return this.name().toLowerCase();
    }
}
