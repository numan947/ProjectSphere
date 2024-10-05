package com.numan947.pmbackend.primary_packages.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetadataType {
    TAG("tag"),
    CATEGORY("category"),
    LABEL("label");

    private final String value;
    // Method to convert a string to enum
    public static MetadataType fromString(String value) {
        for (MetadataType type : MetadataType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type: " + value);
    }
}