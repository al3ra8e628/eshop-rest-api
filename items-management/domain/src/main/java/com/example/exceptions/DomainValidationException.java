package com.example.exceptions;

import lombok.*;

import java.util.Set;

@Getter
public class DomainValidationException extends RuntimeException {
    private final Set<ValidationErrorDetails> validationErrors;

    public DomainValidationException(Set<ValidationErrorDetails> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public static DomainValidationException of(String fieldName, String errorMessage) {
        return new DomainValidationException(Set.of(new ValidationErrorDetails(fieldName, errorMessage)));
    }

    @Data
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class ValidationErrorDetails {
        private String fieldName;
        private String errorMessage;

        public static ValidationErrorDetails of(String fieldName,
                                                String errorMessage) {
            return new ValidationErrorDetails(fieldName, errorMessage);
        }
    }

}
