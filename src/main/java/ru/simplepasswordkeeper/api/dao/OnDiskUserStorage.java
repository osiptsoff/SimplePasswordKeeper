package ru.simplepasswordkeeper.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.User;
import ru.simplepasswordkeeper.api.util.EncryptionUtilImpl;
import ru.simplepasswordkeeper.api.util.FileSystemUtilImpl;
import ru.simplepasswordkeeper.api.util.GzipCompressionUtil;
import ru.simplepasswordkeeper.api.util.ObjectStreamUserSerializationUtil;
import ru.simplepasswordkeeper.api.util.interfaces.CompressionUtil;
import ru.simplepasswordkeeper.api.util.interfaces.EncryptionUtil;
import ru.simplepasswordkeeper.api.util.interfaces.FileSystemUtil;
import ru.simplepasswordkeeper.api.util.interfaces.UserSerializationUtil;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class OnDiskUserStorage implements UserStorage {
    private final CompressionUtil compressionUtil;
    private final EncryptionUtil encryptionUtil;
    private final FileSystemUtil fileSystemUtil;
    private final UserSerializationUtil userSerializationUtil;
    private String folderPath;

    /**
     * <p>Creates {@link OnDiskUserStorage} with given parameters.<p/>
     * @param compressionUtil {@link GzipCompressionUtil} to use,
     * @param encryptionUtil {@link EncryptionUtilImpl} to use,
     * @param fileSystemUtil {@link FileSystemUtilImpl} to use,
     * @param userSerializationUtil {@link ObjectStreamUserSerializationUtil} to use.
     */
    @Autowired
    public OnDiskUserStorage(GzipCompressionUtil compressionUtil, EncryptionUtilImpl encryptionUtil,
                             FileSystemUtilImpl fileSystemUtil, ObjectStreamUserSerializationUtil userSerializationUtil) {
        this.compressionUtil = compressionUtil;
        this.encryptionUtil = encryptionUtil;
        this.fileSystemUtil = fileSystemUtil;
        this.userSerializationUtil = userSerializationUtil;

        folderPath = "users";
    }

    @Override
    public List<String> getUsernames() {
        try {
            // logging
            return List.of(fileSystemUtil.getContentNames(folderPath));
        } catch (Exception e) {
            // logging
            return Collections.emptyList();
        }
    }

    /**
     * @throws IOException if I/O error has occured (e.g. user does not exist),
     * @throws GeneralSecurityException if encryption configured incorrectly (unlikely),
     * @throws IllegalArgumentException if ciphered object has wrong format i.e. data is corrupted,
     * @throws ObjectStreamException if serialization was not successfull.
     */
    @Override
    public User getUser(String name, String password) throws IOException, GeneralSecurityException,
            IllegalArgumentException, ObjectStreamException {
        byte[] userBytes = fileSystemUtil.ReadFromFile(folderPath + "/" + name);
        userBytes = compressionUtil.decompress(userBytes);
        userBytes = encryptionUtil.decrypt(new String(userBytes), password);
        User user = userSerializationUtil.deserialize(userBytes);
        return user;
    }

    /**
     * @throws GeneralSecurityException if encryption configured incorrectly (unlikely),
     * @throws IOException if I/O error has occured (e.g. file cannot be created).
     */
    @Override
    public void saveUser(User user, String password) throws GeneralSecurityException, IOException {
        byte[] userBytes = userSerializationUtil.serialize(user);
        String userString = encryptionUtil.encrypt(userBytes, password);
        userBytes = compressionUtil.compress(userString.getBytes());
        fileSystemUtil.WriteToFile(folderPath + "/" + user.getName(), userBytes, false);
    }

    /**
     * @return path to folder where users will be kept and got from.
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * <p>Sets path to folder where users will be kept and got from.</p>
     * @param folderPath path to folder.
     * @throws IllegalArgumentException if given path is {@code null} or empty.
     */
    public void setFolderPath(String folderPath) throws IllegalArgumentException {
        if(folderPath == null || folderPath.isBlank())
            throw new IllegalArgumentException();
        this.folderPath = folderPath;
    }
}
