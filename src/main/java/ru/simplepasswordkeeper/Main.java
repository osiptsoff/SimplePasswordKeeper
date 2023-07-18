package ru.simplepasswordkeeper;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.simplepasswordkeeper.api.dao.OnDiskUserStorage;
import ru.simplepasswordkeeper.api.model.Account;
import ru.simplepasswordkeeper.api.model.User;

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

    public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {
        var context = new AnnotationConfigApplicationContext(Main.class);
        var storage = context.getBean(OnDiskUserStorage.class);

       var user = new User("Alexey");
       var facebookAccount = new Account("Facebook");
       var amazonAccount = new Account("Amazon");

       facebookAccount.putProperty("login", "alex");
       facebookAccount.putProperty("password", "123");

       amazonAccount.putProperty("login", "alexxx");
       amazonAccount.putProperty("password", "1956");

       user.putAccount(facebookAccount);
       user.putAccount(amazonAccount);

       storage.setFolderPath("/home/badger/Repos/folder");
        storage.saveUser(user, "123");

        var newu = storage.getUser("Alexey", "123");

        System.out.println(newu.equals(user));
   }
}