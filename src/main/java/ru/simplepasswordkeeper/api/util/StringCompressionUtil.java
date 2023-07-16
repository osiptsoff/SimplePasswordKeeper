package ru.simplepasswordkeeper.api.util;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Class dedicated to compressing and decompressing strings using gzip.</p>
 * @author Nikita Osiptsov
 */
@Component
public class StringCompressionUtil {
    /**
     * <p>Compresses given string.</p>
     * @param string string to compress.
     * @return compressed bytes of string.
     * @throws IOException if an I/O error has occurred.
     */
    public byte[] compressString(String string) throws IOException {
        if(string == null || string.length() == 0)
            return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(stream);
        gzipStream.write(string.getBytes());

        String output = stream.toString();

        gzipStream.close();
        stream.close();

        return stream.toByteArray();
    }

    /**
     * <p>Decompresses given array of bytes and returns its string representation.</p>
     * @param string byte representation of compressed string.
     * @return decompressed string.
     * @throws IOException if an I/O error has occurred.
     */
    public String decompressString(byte[] string) throws IOException {
        if(string == null || string.length == 0)
            return null;

        ByteArrayInputStream stream = new ByteArrayInputStream(string);
        GZIPInputStream gzipStream = new GZIPInputStream(stream);

        String result = new String(gzipStream.readAllBytes());

        gzipStream.close();
        stream.close();

        return result;
    }
}
