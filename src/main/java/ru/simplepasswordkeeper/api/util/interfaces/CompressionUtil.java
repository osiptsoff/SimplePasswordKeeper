package ru.simplepasswordkeeper.api.util.interfaces;

import java.io.IOException;

public interface CompressionUtil {
    byte[] compress(byte[] data) throws IOException;
    byte[] decompress(byte[] data) throws IOException;
}
