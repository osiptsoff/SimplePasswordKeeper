package ru.simplepasswordkeeper.api.dao;

/**
 * <p>Indicates if some problem appeared while processing (saving, getting etc.) user.</p>
 * @author Nikita Osiptsov
 */
public class UserProcessingException extends RuntimeException {
    public UserProcessingException(String message) { super(message); }
}
