package ru.simplepasswordkeeper.api.dao;

/**
 * <p>Indicates if storage cannot access user by any reason.</p>
 * @author Nikita Osiptsov
 */
public class UserAccessException extends RuntimeException {
    public UserAccessException() {super(); }
    public UserAccessException(String message) { super(message); }

}
