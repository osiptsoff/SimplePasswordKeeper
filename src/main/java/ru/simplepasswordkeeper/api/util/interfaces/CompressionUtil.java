package ru.simplepasswordkeeper.api.util.interfaces;

import java.io.IOException;

/**
 * <p>Classes implementing this interface are designed to compress and recompress data.</p>
 * @author Nilita Osiptsov
 */
public interface CompressionUtil {
    /**
     * <p>Compresses given array of bytes.</p>
     * @param data data to compress.
     * @return compressed data.
     * @throws IOException if an I/O error has occurred.
     */
    byte[] compress(byte[] data) throws IOException;

    /**
     * <p>Decompresses given array of bytes.</p>
     * @param data data to decompress.
     * @return decompressed data.
     * @throws IOException if an I/O error has occurred.
     */
    byte[] decompress(byte[] data) throws IOException;
}
