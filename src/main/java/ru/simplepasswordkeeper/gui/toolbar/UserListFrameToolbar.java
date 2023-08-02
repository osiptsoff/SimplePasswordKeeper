package ru.simplepasswordkeeper.gui.toolbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.dao.UserStorage;
import ru.simplepasswordkeeper.api.model.User;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.Prompt;
import ru.simplepasswordkeeper.gui.component.ScrollableList;
import ru.simplepasswordkeeper.gui.frame.AccountListWindow;

import javax.swing.JOptionPane;

@Component
public final class UserListFrameToolbar extends Toolbar {
    private final Prompt userCreationPrompt;
    private final Prompt passwordPrompt;
    private final AccountListWindow accountListWindow;

    private ScrollableList<String> list;
    private UserStorage userStorage;

    @Autowired
    public UserListFrameToolbar(@Qualifier("userCreationPrompt") Prompt userCreationPrompt,
                                @Qualifier("passwordPrompt") Prompt passwordPrompt,
                                AccountListWindow userListWindow) {
        this.userCreationPrompt = userCreationPrompt;
        this.passwordPrompt = passwordPrompt;
        this.accountListWindow = userListWindow;
    }

    public void setList(ScrollableList<String> list) {
        this.list = list;
    }

    public void setUserStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    void create() {
        int res = userCreationPrompt.showPrompt();

        if(res == JOptionPane.OK_OPTION) {
            String username = userCreationPrompt.getValue(Constants.UsernameInputMessage);
            String password = userCreationPrompt.getValue(Constants.PasswordInputMessage);
            String passwordConfirm = userCreationPrompt.getValue(Constants.ConfirmPasswordMessage);

            if(list.contains(username)) {
                JOptionPane.showMessageDialog(null, Constants.UserExistsMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!password.equals(passwordConfirm)) {
                JOptionPane.showMessageDialog(null, Constants.PasswordDontMatchMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                userStorage.saveUser(new User(username), password);
                list.addElement(username);

                userCreationPrompt.clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,  Constants.UserCreationExceptionCaughtMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    void remove() {
        String name = list.getSelectedElement();
        if(name == null) {
            JOptionPane.showMessageDialog(null, Constants.ObjectNotSelectedMessage,
                    Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = passwordPrompt.showPrompt();

        if(res == JOptionPane.OK_OPTION) {
           try {
                userStorage.getUser(name, passwordPrompt.getValue(Constants.PasswordPromptPasswordField));
                userStorage.deleteUser(name);
                list.removeElement(name);

                passwordPrompt.clearFields();
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null,  Constants.UserFrameDeletionExceptionCaughtMessage,
                       Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
           }
        }
    }

    @Override
    void edit() {
        String name = list.getSelectedElement();
        if(name == null) {
            JOptionPane.showMessageDialog(this, Constants.ObjectNotSelectedMessage,
                    Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = passwordPrompt.showPrompt();

        if(res == JOptionPane.OK_OPTION) {
            try {
                User user = userStorage.getUser(name, passwordPrompt.getValue(Constants.PasswordPromptPasswordField));
                accountListWindow.setUser(user);
                accountListWindow.setPassword(passwordPrompt.getValue(Constants.PasswordPromptPasswordField));
                res = JOptionPane.showConfirmDialog(this, accountListWindow, null,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(res == JOptionPane.OK_OPTION) {
                    if(!name.equals(user.getName()))
                        try {
                            userStorage.deleteUser(name);
                        } catch (Exception e) {
                            // Do nothing: file already deleted or cannot be accessed
                        } finally {
                            list.removeElement(name);
                            list.addElement(user.getName());
                        }

                    userStorage.saveUser(user, accountListWindow.getPassword());
                }
                passwordPrompt.clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,  Constants.UserFrameEditionExceptionCaughtMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    void help() {
        JOptionPane.showMessageDialog(null,  Constants.UserWindowInfoMessage,
                Constants.infoMessageHeader, JOptionPane.INFORMATION_MESSAGE);
    }
}
