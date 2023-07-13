package ru.simplepasswordkeeper.api.dao;

import ru.simplepasswordkeeper.api.model.User;

import java.util.List;

public interface UserStorage {
    public List<String> getUsernames();
    public User getUser(String name, String key);
    public void saveUser(User user);
}
