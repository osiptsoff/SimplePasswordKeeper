package ru.simplepasswordkeeper.api.util;

import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.util.interfaces.CompressionUtil;

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
public class GzipCompressionUtil implements CompressionUtil {
    /**
     * <p>Compresses given array of bytes.</p>
     * @param data data to compress.
     * @return compressed data.
     * @throws IOException if an I/O error has occurred.
     */
    @Override
    public byte[] compress(byte[] data) throws IOException {
        if(data == null || data.length == 0)
            return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(stream);
        gzipStream.write(data);

        gzipStream.close();

        byte[] output = stream.toByteArray();

        return output;
    }

    /**
     * <p>Decompresses given array of bytes.</p>
     * @param data data to decompress.
     * @return decompressed data.
     * @throws IOException if an I/O error has occurred.
     */
    @Override
    public byte[] decompress(byte[] data) throws IOException {
        if(data == null || data.length == 0)
            return null;

        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        GZIPInputStream gzipStream = new GZIPInputStream(stream);

        byte[] result = gzipStream.readAllBytes();

        gzipStream.close();

        return result;
    }
}
