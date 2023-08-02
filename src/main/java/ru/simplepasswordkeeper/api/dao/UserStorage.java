package ru.simplepasswordkeeper.api.dao;

import ru.simplepasswordkeeper.api.model.User;

import java.util.List;

/**
 * <p>Interface providing methods for storing {@link User}s.</p>
 * @author Nikita Osiptsov
 */
public interface UserStorage {
    /**
     * @return list of names of all kept users.
     */
     List<String> getUsernames();

    /**
     * @param name name of required user,
     * @param password password user to access user data.
     * @return required user.
     * @throws UserAccessException if storage failed to access user,
     * @throws UserProcessingException if any error occurred while processing user.
     */
     User getUser(String name, String password) throws UserAccessException, UserProcessingException;

    /**
     * <p>Saves given user to storage.</p>
     * @param user user to save.
     * @param password password of user, used to create key for encryption.
     * @throws UserProcessingException if any error occurred while processing user.
     */
     void saveUser(User user, String password) throws UserProcessingException;

    /**
     * <p>Deletes user with given name.</p>
     * @param name name of user.
     * @throws UserProcessingException if any error occurred while processing user.
     */
     void deleteUser(String name) throws UserAccessException, UserProcessingException;
}
