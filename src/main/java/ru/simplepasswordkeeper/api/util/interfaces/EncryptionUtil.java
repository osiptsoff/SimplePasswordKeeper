package ru.simplepasswordkeeper.api.util.interfaces;

import java.security.GeneralSecurityException;

/**
 * <p>Classes implementing this interface are designed to encrypt and decrypt data.</p>
 * @author Nilita Osiptsov
 */
public interface EncryptionUtil {
    /**
     * <p>Performs encryption of data, then Base64 encodes it.</p>
     * @param data data to encrypt,
     * @param password password to create key from.
     * @return encoded data.
     * @throws GeneralSecurityException if cipher work caused exceptions (unlikely situation).
     */
    String encrypt(byte[] data, String password) throws GeneralSecurityException;
    /**
     * <p>Base64 decodes string, then decrypts it using AES.<p/>
     * @param string string to decrypt.
     * @param password password to create key from.
     * @return decoded and decrypted "data" part of input string.
     * @throws IllegalArgumentException if input string is not of iv$data$salt format.
     * @throws GeneralSecurityException if cipher work caused exceptions (unlikely situation).
     */
    byte[] decrypt(String string, String password) throws GeneralSecurityException;
}
