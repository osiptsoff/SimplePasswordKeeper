package ru.simplepasswordkeeper.api.util.interfaces;

import ru.simplepasswordkeeper.api.model.User;

import java.io.IOException;
import java.io.ObjectStreamException;

/**
 * <p>Classes implementing this interface are designed to serialize and deserialize {@link User}s.</p>
 * @author Nilita Osiptsov
 */
public interface UserSerializationUtil {
    /**
     * <p>Converts given {@link User} instance to array of bytes.</p>
     * <p>User can be completely restored from this array with {@link #deserialize(byte[])} method.</p>
     * @param user User to serialize.
     * @return serialized user.
     * @throws IOException if an I/O error occurred.
     */
    byte[] serialize(User user) throws IOException;
    /**
     * <p>Converts given array of bytes to {@link User} instance.</p>
     * <p>Arrays created by {@link #serialize(User)} will suffice.</p>
     * @param data User stored as array of bytes.
     * @return restored User.
     * @throws IOException if an I/O error occurred,
     * @throws ObjectStreamException if deserialzation was unsuccessful.
     */
    User deserialize(byte[] data) throws IOException;

}
