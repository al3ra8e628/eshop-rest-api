package com.example.validators;

import com.example.contract.requests.ItemRequest;
import com.example.exceptions.DomainValidationException;
import com.example.exceptions.DomainValidationException.ValidationErrorDetails;
import com.example.modals.ItemCategory;

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


public abstract class ItemRequestValidator<T extends ItemRequest> {

    public void validate(T request) {
        final Set<ValidationErrorDetails> validationErrors = new HashSet<>();

        if (Objects.isNull(getCategory(request))) {
            validationErrors.add(of(CATEGORY_FIELD_NAME, VALUE_REQUIRED_ERROR_MESSAGE));
        }

        if (!hasValidRatingValueRange(request)) {
            validationErrors.add(of(RATING_FIELD_NAME, INVALID_RATING_VALUE_RANGE));
        }

        throwIfHaveErrors(validationErrors);
    }

    protected abstract int getRating(T itemRequest);
    protected abstract ItemCategory getCategory(T request);

    private boolean hasValidRatingValueRange(T itemRequest) {
        return Objects.isNull(getRating(itemRequest)) || (getRating(itemRequest) > 0 && getRating(itemRequest) <= 5);
    }


    private void throwIfHaveErrors(Set<ValidationErrorDetails> validationErrors) {
        if (!validationErrors.isEmpty()) {
            throw new DomainValidationException(validationErrors);
        }
    }

}
