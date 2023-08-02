package ru.simplepasswordkeeper.gui.toolbar;

import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.Account;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.MapTable;

import javax.swing.JOptionPane;

@Component
public final  class AccountPropertiesWindowToolbar extends Toolbar {
    private Account account;
    private MapTable table;


    public void setAccount(Account account) throws NullPointerException {
        if(account == null)
            throw new NullPointerException();

        this.account = account;
    }

    public void setTable(MapTable table) throws NullPointerException {
        if(table == null)
            throw new NullPointerException();

        this.table = table;
    }

    @Override
    void create() {
        table.addEmptyRow();
    }

    @Override
    void remove() {
        int selectedRow = table.getSelectedRow();

        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, Constants.ObjectNotSelectedMessage,
                    Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
            return;
        }

        table.removeRow(selectedRow);
    }

    @Override
    void edit() {
        this.help();
    }

    @Override
    void help() {
        JOptionPane.showMessageDialog(null,  Constants.AccountPropertiesWindowInfoMessage,
                Constants.infoMessageHeader, JOptionPane.INFORMATION_MESSAGE);
    }
}
