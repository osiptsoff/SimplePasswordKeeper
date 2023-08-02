package ru.simplepasswordkeeper.gui.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.model.Account;
import ru.simplepasswordkeeper.gui.Constants;
import ru.simplepasswordkeeper.gui.component.MapTable;
import ru.simplepasswordkeeper.gui.component.Prompt;
import ru.simplepasswordkeeper.gui.toolbar.AccountPropertiesWindowToolbar;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

@Component
public class AccountPropertiesWindow extends JInternalFrame {
    private String accName;
    private final MapTable table;
    private final Prompt accnamePrompt;
    private final AccountPropertiesWindowToolbar toolbar;

    @Autowired
    public AccountPropertiesWindow(MapTable table,
                                   AccountPropertiesWindowToolbar toolbar,
                                   @Qualifier("accnamePrompt") Prompt accnamePrompt) {
        this.table = table;
        this.toolbar = toolbar;
        toolbar.setTable(table);
        this.accnamePrompt = accnamePrompt;

        JButton changeNameButton = new JButton("Сменить имя");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension((int)screen.getWidth() / 3, (int)screen.getHeight() / 2));
        this.pack();
        this.setVisible(true);

        this.add(toolbar, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
        this.add(changeNameButton, BorderLayout.SOUTH);

        this.addListeners(changeNameButton);
    }

    public void setAccount(Account account) throws NullPointerException {
        if(account == null)
            throw new NullPointerException();

        accName = account.getName();
        toolbar.setAccount(account);
        table.clearAndFill(account.getProperties());
    }

    public String getNewAccname() {
        return accName;
    }

    public Map<String, String> getNewProps() {
        return table.collectMap();
    }

    private void addListeners(JButton changeNameButton) {
        changeNameButton.addActionListener(e -> {
            int res = accnamePrompt.showPrompt();

            if(res == JOptionPane.OK_OPTION) {
                String name = accnamePrompt.getValue(Constants.AccnamePromptAccnameField);

                if(name == null || name.isBlank()) {
                    JOptionPane.showMessageDialog(null, Constants.InvalidNameMessage,
                            Constants.infoMessageHeader, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                accName = name;
            }
        });
    }
}
