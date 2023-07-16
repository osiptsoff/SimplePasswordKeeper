package ru.simplepasswordkeeper;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.simplepasswordkeeper.api.util.FileSystemUtil;
import ru.simplepasswordkeeper.api.util.CompressionUtil;
import ru.simplepasswordkeeper.api.util.EncryptionUtil;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
@ComponentScan
public class Main {
    @Bean
    public Cipher cipher() {
        try {
            return Cipher.getInstance("AES/CTR/NoPadding");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        var context = new AnnotationConfigApplicationContext(Main.class);
        var util = new FileSystemUtil();
        var util1 = context.getBean(EncryptionUtil.class);
        var util2 = new CompressionUtil();

        // try read data and compress; sizes in bytes out (2)
        String example = new String(util.ReadFromFile("/home/badger/Repos/folder/hello"));
        //System.out.println(example);
        System.out.println(example.getBytes().length);
        System.out.println(util2.compress(example.getBytes()).length);

        // try encrypt data; size in bytes out (1)
        String encoded = util1.encrypt(example.getBytes(), "123");
        //System.out.println(encoded);
        System.out.println(encoded.getBytes().length);

        // try compress encrypted data and write on disk; size out (1)
        byte[] encryptedCompressed = util2.compress(encoded.getBytes());
        util.WriteToFile("/home/badger/Repos/folder/b.txt", encryptedCompressed, false);
        //System.out.println(new String(encryptedCompressed));
        System.out.println(encryptedCompressed.length);

        // try read data from disk, decrypt and decompress it
        byte[] readEncryptedCompressed = util.ReadFromFile("/home/badger/Repos/folder/b.txt");
        //System.out.println(new String(readEncryptedCompressed));
        var encryptedDecompressed = util2.decompress(readEncryptedCompressed);
        //System.out.println(new String(encryptedDecompressed));
        var decryptedDecompressed = new String(util1.decrypt(new String(encryptedDecompressed), "123"));
        //System.out.println(new String(decryptedDecompressed));

        // check equality
        System.out.println(decryptedDecompressed.equals(example));
    }
}