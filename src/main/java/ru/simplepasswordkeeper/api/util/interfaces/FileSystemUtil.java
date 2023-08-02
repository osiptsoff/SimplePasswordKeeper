package ru.simplepasswordkeeper.api.util.interfaces;

import java.io.IOException;

/**
 * <p>Classes implementing this interface are designed to traverse filesystem.</p>
 * @author Nilita Osiptsov
 */
public interface FileSystemUtil {
    /**
     * <p>Writes given data to file with given path.</p>
     * @param filename name of file,
     * @param data data to write to file,
     * @param append value indicating whether to append data to file or not:
     *               append if {@code true}, overwrite file otherwise.
     * @throws IOException if file with given name cannot be created or
     * exists and application has no rights to access it.
     */
    void WriteToFile(String filename, Object data, boolean append) throws IOException;

    /**
     * <p>Reads data from given file.<p/>
     * @param filename name of file.
     * @return file contents.
     * @throws IOException if file does not exist or application has no rights to access it.
     */
    byte[] ReadFromFile(String filename) throws IOException;

    /**
     * @param path path to directory.
     * @return names of files located in given directory.
     * @throws IllegalArgumentException if given file does not exist or is not directory,
     * @throws SecurityException if application has no rights to access given file.
     */
    String[] getContentNames(String path) throws IllegalArgumentException, SecurityException;

    /**
     * <p>Deletes file with given path.</p>
     * @param path path to file.
     * @throws IllegalArgumentException if deletion was not successfull by any reason not covered
     * by {@code SecurityException},
     * @throws SecurityException if application has no rights to access given file.
     */
    void deleteFile(String path) throws IllegalArgumentException, SecurityException;

    /**
     * <p>Checks if given file exists.</p>
     * @param path path to file.
     * @return {@code true} if file exists, {@code false} otherwise.
     */
    boolean fileExists(String path);

}
