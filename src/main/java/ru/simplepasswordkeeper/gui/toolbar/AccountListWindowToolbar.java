package ru.simplepasswordkeeper.gui.toolbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.Account;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.Prompt;
import ru.simplepasswordkeeper.gui.component.ScrollableList;
import ru.simplepasswordkeeper.gui.frame.AccountPropertiesWindow;

import javax.swing.JOptionPane;
import java.util.List;

@Component
public final class AccountListWindowToolbar extends Toolbar {
    private List<Account> accounts;
    private ScrollableList<Account> list;

    private final Prompt accnamePrompt;
    private final AccountPropertiesWindow accountPropertiesWindow;

    @Autowired
    public AccountListWindowToolbar(@Qualifier("accnamePrompt") Prompt accnamePrompt,
                                    AccountPropertiesWindow accountPropertiesWindow) {
        this.accnamePrompt = accnamePrompt;
        this.accountPropertiesWindow = accountPropertiesWindow;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setList(ScrollableList<Account> list) {
        this.list = list;
    }


    @Override
    void create() {
        int res = accnamePrompt.showPrompt();

        if(res == JOptionPane.OK_OPTION)
            try {
                String accname = accnamePrompt.getValue(Constants.AccnamePromptAccnameField);
                Account account = new Account(accname);
                accounts.add(account);
                list.addElement(account);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, Constants.AccCrreationExceptionCaughtMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            }
    }

    @Override
    void remove() {
        Account acc = list.getSelectedElement();

        if(acc == null) {
            JOptionPane.showMessageDialog(null, Constants.ObjectNotSelectedMessage,
                    Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = JOptionPane.showConfirmDialog(null, Constants.AccDeleteConfirmMessage,
                Constants.infoMessageHeader, JOptionPane.YES_NO_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            accounts.remove(acc);
            list.removeElement(acc);
        }
    }

    @Override
    void edit() {
        Account selected = list.getSelectedElement();
        if(selected == null) {
            JOptionPane.showMessageDialog(this, Constants.ObjectNotSelectedMessage,
                    Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            return;
        }

        accountPropertiesWindow.setAccount(selected);

        int res = JOptionPane.showConfirmDialog(this, accountPropertiesWindow, null,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if(res == JOptionPane.OK_OPTION)
            try {
                list.repaint();
                selected.setName(accountPropertiesWindow.getNewAccname());
                selected.getProperties().clear();
                selected.putProperty(accountPropertiesWindow.getNewProps());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, Constants.FailedChangeAccountMessage,
                        Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            }

    }

    @Override
    void help() {
        JOptionPane.showMessageDialog(null,  Constants.AccountWindowInfoMessage,
                Constants.infoMessageHeader, JOptionPane.INFORMATION_MESSAGE);
    }
}
