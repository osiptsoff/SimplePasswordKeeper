package ru.simplepasswordkeeper.api.util.interfaces;

import ru.simplepasswordkeeper.api.model.User;

import java.io.IOException;

public interface UserSerializationUtil {
    byte[] serialize(User user) throws IOException;
    User deserialize(byte[] data) throws IOException;

}
