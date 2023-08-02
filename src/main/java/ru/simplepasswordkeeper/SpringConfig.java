package ru.simplepasswordkeeper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.Prompt;

import javax.crypto.Cipher;

@Configuration
@ComponentScan
public class SpringConfig {
    @Bean
    public Cipher cipher() {
        try {
            return Cipher.getInstance("AES/CTR/NoPadding");
        } catch(Exception e) {
            return null;
        }
    }

    @Bean
    public Prompt userCreationPrompt() {
        Prompt res = new Prompt();

        res.setMessage(Constants.UsernameAndPasswordInputMessage);
        res.setTextFieldLength(15);
        res.addField(Constants.UsernameInputMessage, false);
        res.addField(Constants.PasswordInputMessage, true);
        res.addField(Constants.ConfirmPasswordMessage, true);

        return res;
    }

    @Bean
    public Prompt passwordPrompt() {
        Prompt res = new Prompt();

        res.setMessage(Constants.PasswordPromptMessage);
        res.setTextFieldLength(10);
        res.addField(Constants.PasswordPromptPasswordField, true);

        return res;
    }

    @Bean
    public Prompt passwordPromptConfirmed() {
        Prompt res = new Prompt();

        res.setMessage(Constants.PasswordPromptMessage);
        res.setTextFieldLength(15);
        res.addField(Constants.PasswordInputMessage, true);
        res.addField(Constants.ConfirmPasswordMessage, true);

        return res;
    }

    @Bean
    public Prompt usernamePrompt() {
        Prompt res = new Prompt();

        res.setMessage(Constants.UsernamePromptMessage);
        res.setTextFieldLength(15);
        res.addField(Constants.UsernameInputMessage, false);

        return res;
    }

    @Bean
    public Prompt accnamePrompt() {
        Prompt res = new Prompt();

        res.setMessage(Constants.AccnamePromptHeader);
        res.setTextFieldLength(15);
        res.addField(Constants.AccnamePromptAccnameField, false);

        return res;
    }
}
