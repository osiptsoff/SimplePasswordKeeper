package ru.simplepasswordkeeper;

import ru.simplepasswordkeeper.api.model.Account;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account("Amazon");
        acc.putProperty("login", "baab");
        acc.putProperty("password", "123");

        for(var entry : acc.getProperties().entrySet())
            System.out.println(entry);
    }
}