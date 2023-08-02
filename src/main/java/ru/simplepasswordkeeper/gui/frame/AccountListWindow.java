package ru.simplepasswordkeeper.gui.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.Account;
import ru.simplepasswordkeeper.api.model.User;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.Prompt;
import ru.simplepasswordkeeper.gui.component.ScrollableList;
import ru.simplepasswordkeeper.gui.toolbar.AccountListWindowToolbar;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

@Component
public final class AccountListWindow extends JInternalFrame {
    private User user;
    private String password;
    private final Prompt passwordPrompt, usernamePrompt;
    private final ScrollableList<Account> list;
    private final AccountListWindowToolbar toolbar;

    @Autowired
    public AccountListWindow(AccountListWindowToolbar toolbar,
                             ScrollableList<Account> list,
                             @Qualifier("passwordPromptConfirmed") Prompt passwordPrompt,
                             @Qualifier("usernamePrompt") Prompt usernamePrompt) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel buttonPanel = new JPanel();
        JButton passwordChangeButton = new JButton("Сменить пароль");
        JButton usernameChangeButton = new JButton("Сменить имя");

        this.toolbar = toolbar;
        toolbar.setList(list);
        this.list = list;
        this.passwordPrompt = passwordPrompt;
        this.usernamePrompt = usernamePrompt;

        buttonPanel.add(passwordChangeButton, BorderLayout.WEST);
        buttonPanel.add(usernameChangeButton, BorderLayout.EAST);

        this.add(toolbar, BorderLayout.NORTH);
        this.add(list, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension((int)screen.getWidth() / 3, (int)screen.getHeight() / 2));
        this.pack();
        this.setVisible(true);

        addListeners(passwordChangeButton, usernameChangeButton);
    }

    public void setUser(User user) throws NullPointerException {
        if(user == null)
            throw new NullPointerException();

        list.clear();
        user.getAccounts().forEach(list::addElement);
        toolbar.setAccounts(user.getAccounts());

        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private void addListeners(JButton passwordChangeButton, JButton usernameChangeButton) {
        passwordChangeButton.addActionListener(e -> {
            int res = passwordPrompt.showPrompt();

            if(res == JOptionPane.OK_OPTION) {
                String password = passwordPrompt.getValue(Constants.PasswordInputMessage);
                String confirmPassword = passwordPrompt.getValue(Constants.ConfirmPasswordMessage);

                if(!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, Constants.PasswordDontMatchMessage,
                            Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                passwordPrompt.clearFields();
                this.setPassword(password);
            }
        });

        usernameChangeButton.addActionListener(e -> {
            int res = usernamePrompt.showPrompt();

            if(res == JOptionPane.OK_OPTION) {
                String username = usernamePrompt.getValue(Constants.UsernameInputMessage);

                if(username == null || username.isBlank()) {
                    JOptionPane.showMessageDialog(null, Constants.UsernameIncorrectMessage,
                            Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                usernamePrompt.clearFields();
                user.setName(username);
            }
        });
    }
}
