package ru.simplepasswordkeeper.api.util.interfaces;

import java.security.GeneralSecurityException;

public interface EncryptionUtil {
    String encrypt(byte[] data, String password) throws GeneralSecurityException;
    byte[] decrypt(String string, String password) throws GeneralSecurityException;
}
