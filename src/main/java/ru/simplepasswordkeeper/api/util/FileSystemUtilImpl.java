package ru.simplepasswordkeeper.api.util;

import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.util.interfaces.FileSystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>Minimal functionality class which encapsulates necessary work with files.</p>
 * @author Nikita Osiptsov
 */
@Component
public class FileSystemUtilImpl implements FileSystemUtil {
    /**
     * <p>Writes given data to file with given path.</p>
     * <p>If given data is {@code String}, its bytes will be stored in file; if data is {@code byte[]}, it will be
     * stored in file; otherwise {@link #toString()} will be invoked and then bytes will be stored.</p>
     */
    @Override
    public void WriteToFile(String filename, Object data, boolean append) throws IOException {
        FileOutputStream stream = new FileOutputStream(filename, append);

        stream.write(Resolve(data));
        stream.close();
    }

    @Override
    public byte[] ReadFromFile(String filename) throws IOException {
        FileInputStream stream = new FileInputStream(filename);

        var result = stream.readAllBytes();
        stream.close();

        return result;
    }

    @Override
    public String[] getContentNames(String path) throws IllegalArgumentException, SecurityException {
        if(path == null || path.isBlank())
            throw new IllegalArgumentException();

        File file = new File(path);

        if(!file.isDirectory())
            throw new IllegalArgumentException();

        return file.list();
    }

    @Override
    public void deleteFile(String path) throws IllegalArgumentException, SecurityException {
        if(path == null || path.isBlank())
            throw new IllegalArgumentException();

        File file = new File(path);

        if(!file.delete())
            throw new IllegalArgumentException();
    }

    private byte[] Resolve(Object object) {
        if (object instanceof byte[])
            return (byte[])object;

        return object.toString().getBytes();
    }
}
