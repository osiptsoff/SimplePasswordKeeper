package ru.simplepasswordkeeper.api.util;

import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.User;
import ru.simplepasswordkeeper.api.util.interfaces.UserSerializationUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.StreamCorruptedException;

/**
 * <p>Class for serialization of {@link User} instances.</p>
 * @author Nikita Osiptsov
 */
@Component
public class ObjectStreamUserSerializationUtil implements UserSerializationUtil {
    /**
     * <p>Converts given {@link User} instance to array of bytes.</p>
     * <p>User can be completely restored from this array with {@link #deserialize(byte[])} method.</p>
     * @param user User to serialize.
     * @return serialized user.
     * @throws IOException if an I/O error occurred.
     */
    @Override
    public byte[] serialize(User user) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(stream);

        objStream.writeObject(user);
        objStream.close();

        return stream.toByteArray();
    }

    /**
     * <p>Converts given array of bytes to {@link User} instance.</p>
     * <p>Arrays created by {@link #serialize(User)} will suffice.</p>
     * <p>Note that situation when this method successfully restores corrupted data is possible.</p>
     * @param data User stored as array of bytes.
     * @return restored User.
     * @throws IOException if an I/O error occurred,
     * @throws ObjectStreamException if deserialzation was unsuccessful.
     */
    @Override
    public User deserialize(byte[] data) throws IOException, ObjectStreamException {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        ObjectInputStream objStream = new ObjectInputStream(stream);

        try {
            User user = (User) objStream.readObject();
            objStream.close();
            return user;
        } catch (ClassNotFoundException e) {
            throw new StreamCorruptedException();
        }
    }
}
