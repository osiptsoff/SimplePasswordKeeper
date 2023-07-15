package ru.simplepasswordkeeper;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.simplepasswordkeeper.api.util.FileSystemUtil;
import ru.simplepasswordkeeper.api.util.StringCompressionUtil;
import ru.simplepasswordkeeper.api.util.StringEncryptionUtil;

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
        System.out.println("FUCK");
        return null;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        var context = new AnnotationConfigApplicationContext(Main.class);
        var util = new FileSystemUtil();
        var util1 = context.getBean(StringEncryptionUtil.class);
        var util2 = new StringCompressionUtil();

        // try read data and compress; sizes in bytes out (2)
        String example = new String(util.ReadFromFile("/home/badger/Repos/folder/a.json"));
        //System.out.println(example);
        System.out.println(example.getBytes().length);
        System.out.println(util2.compressString(example).length);

        // try encrypt data; size in bytes out (1)
        String encoded = util1.encryptString(example, "123");
        //System.out.println(encoded);
        System.out.println(encoded.getBytes().length);

        // tru compress encrypted data and write on disk; size out (1)
        byte[] encryptedCompressed = util2.compressString(encoded);
        util.WriteToFile("/home/badger/Repos/folder/b.txt", encryptedCompressed, false);
        //System.out.println(encryptedCompressed);
        System.out.println(encryptedCompressed.length);

        // try read data from disk, decrypt and decompress it
        byte[] readEncryptedCompressed = util.ReadFromFile("/home/badger/Repos/folder/b.txt");
        String encryptedDecompressed = util2.decompressString(readEncryptedCompressed);
        String decryptedDecompressed = util1.decryptString(encryptedDecompressed, "123");

        // check equality
        System.out.println(decryptedDecompressed.equals(example));
    }
}