package ru.simplepasswordkeeper.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * <p>Class dedicated to encryption and decryption of strings.</p>
 * @author Nikita Osiptsov
 */
@Component
public class StringEncryptionUtil {
    private final static int SaltSize = 16,
            hashSize = 128,
            hashIterations = 65536;

    @Autowired
    private Cipher cipher;

    public StringEncryptionUtil() {}

    /**
     * <p>Performs AES encryption of string, then Base64 encodes it.</p>
     * @param string string to encrypt,
     * @param password password to create key from.
     * @return string of following format: iv$data$salt, where data is encrypted and Base64 encoded input string.
     * @throws GeneralSecurityException if cipherer work caused exceptions (unlikely situation).
     */
    public String encryptString(String string, String password) throws GeneralSecurityException {
        Base64.Encoder enc = Base64.getEncoder();
        byte[] iv = generateByteSequence(cipher.getBlockSize());
        byte[] salt = generateByteSequence(SaltSize);
        byte[] inputBytes = string.getBytes();

        byte[] encrypted = performCipherWork(iv, inputBytes, salt, password, Cipher.ENCRYPT_MODE);;

        return enc.encodeToString(iv) + "$" + enc.encodeToString(encrypted) + "$" + enc.encodeToString(salt);
    }

    /**
     * <p>Base64 decodes stringm, then decrypts it using AES.<p/>
     * @param string string to decrypt, of following format: iv$data$salt,
     *              where data is encrypted and Base64 encoded string.
     * @param password password to create key from.
     * @return decoded and decrypted "data" part of input string.
     * @throws IllegalArgumentException if input string is not of iv$data$salt format.
     * @throws GeneralSecurityException if cipherer work caused exceptions (unlikely situation).
     */
    public String decryptString(String string, String password) throws IllegalArgumentException,
            GeneralSecurityException {
        String[] props = string.split("\\$");
        if(props.length != 3)
            throw new IllegalArgumentException();

        Base64.Decoder dec = Base64.getDecoder();
        byte[] iv = dec.decode(props[0]);
        byte[] inputBytes = dec.decode(props[1]);
        byte[] salt = dec.decode(props[2]);

        byte[] decrypted = performCipherWork(iv, inputBytes, salt, password, Cipher.DECRYPT_MODE);

        return new String(decrypted);
    }

    private byte[] performCipherWork(byte[] iv, byte[] input, byte[] salt, String password, int mode)
            throws IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashPassword(password, salt, hashSize), "AES");

        cipher.init(mode, secretKeySpec, ivParameterSpec);
        byte[] output = cipher.doFinal(input);

        return input;
    }

    private byte[] hashPassword(String password, byte[] salt, int size) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, hashIterations, size);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password.getBytes();
    }

    private byte[] generateByteSequence(int size) {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] sequence = new byte[size];
        randomSecureRandom.nextBytes(sequence);

        return sequence;
    }
}
