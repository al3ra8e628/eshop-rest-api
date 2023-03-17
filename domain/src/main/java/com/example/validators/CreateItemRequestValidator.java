package com.example.validators;

import com.example.contract.requests.CreateItemRequest;
import com.example.exceptions.DomainValidationException;
import com.example.exceptions.DomainValidationException.ValidationErrorDetails;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.example.contract.constants.DomainConstants.*;
import static com.example.exceptions.DomainValidationException.ValidationErrorDetails.of;



/*
 *
 *
 * item category is required category
 * rating value should be positive (optional value)
 *
 *
 * validation flow...
 * case: if request has an invalid details
 *      1- notify the executor of this functional that something wrong happened.
 *      2- stop the execution so that no records will be persisted, removed to db.
 *
 * case: if request has no issues then execution will proceed with no interruptions
 *
 *
 * throwing exception...
 * */


public class CreateItemRequestValidator {

    public void validate(CreateItemRequest createItemRequest) {
        final Set<ValidationErrorDetails> validationErrors = new HashSet<>();

        if (Objects.isNull(createItemRequest.getCategory())) {
            validationErrors.add(of(CATEGORY_FIELD_NAME, VALUE_REQUIRED_ERROR_MESSAGE));
        }

        if (!hasValidRatingValueRange(createItemRequest)) {
            validationErrors.add(of(RATING_FIELD_NAME, INVALID_RATING_VALUE_RANGE));
        }

        throwIfHaveErrors(validationErrors);
    }

    private static boolean hasValidRatingValueRange(CreateItemRequest createItemRequest) {
        return Objects.isNull(createItemRequest.getRating()) || (createItemRequest.getRating() > 0 && createItemRequest.getRating() <= 5);
    }

    private void throwIfHaveErrors(Set<ValidationErrorDetails> validationErrors) {
        if (!validationErrors.isEmpty()) {
            throw new DomainValidationException(validationErrors);
        }
    }

}
