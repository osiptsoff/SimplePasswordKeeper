package ru.simplepasswordkeeper.api.util.interfaces;

import java.io.IOException;

public interface FileSystemUtil {
    void WriteToFile(String filename, Object data, boolean append) throws IOException;
    byte[] ReadFromFile(String filename) throws IOException;
    String[] getContentNames(String path) throws IllegalArgumentException, SecurityException;

}
