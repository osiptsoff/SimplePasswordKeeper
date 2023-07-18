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
     * @throws Exception if something wrong.
     */
     User getUser(String name, String password) throws Exception;

    /**
     * <p>Saves given user to storage.</p>
     * @param user user to save.
     * @param password password of user, used to create key for encryption.
     * @throws Exception if something wrong.
     */
     void saveUser(User user, String password) throws Exception;
}
