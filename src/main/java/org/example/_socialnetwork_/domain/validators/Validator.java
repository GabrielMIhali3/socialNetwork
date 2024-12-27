package org.example._socialnetwork_.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
