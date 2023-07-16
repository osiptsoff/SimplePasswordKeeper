package ru.simplepasswordkeeper.api.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>Minimal functionality class which encapsulates necessary work with files.</p>
 * @author Nikita Osiptsov
 */
@Component
public class FileSystemUtil {
    /**
     * <p>Writes given data to file with given path.</p>
     * <p>If given data is {@code String}, its bytes will be stored in file; if data is {@code byte[]}, it will be
     * stored in file; otherwise {@link #toString()} and then bytes will be stored.</p>
     * @param filename name of file,
     * @param data data to write to file,
     * @param append value indicating whether to append data to file or not:
     *               append if {@code true}, overwrite file otherwise.
     * @throws IOException if file with given name cannot be created or
     * exists and application has no rights to access it.
     */
    public void WriteToFile(String filename, Object data, boolean append) throws IOException {
        FileOutputStream stream = new FileOutputStream(filename, append);

        stream.write(Resolve(data));
        stream.close();
    }

    /**
     * <p>Reads data from given file.<p/>
     * @param filename name of file.
     * @return file contents.
     * @throws IOException if file does not exist or application has no rights to access it.
     */
    public byte[] ReadFromFile(String filename) throws IOException {
        FileInputStream stream = new FileInputStream(filename);

        var result = stream.readAllBytes();
        stream.close();

        return result;
    }

    /**
     * @param path path to directory.
     * @return names of files located in given directory.
     * @throws IllegalArgumentException if given file does not exist or is not directory,
     * @throws SecurityException if application has no rights to access given file.
     */
    public String[] getContentNames(String path) throws IllegalArgumentException, SecurityException {
        File file = new File(path);

        if(!file.isDirectory())
            throw new IllegalArgumentException();

        return file.list();
    }

    private byte[] Resolve(Object object) {
        if (object instanceof byte[])
            return (byte[])object;

        return object.toString().getBytes();
    }
}
