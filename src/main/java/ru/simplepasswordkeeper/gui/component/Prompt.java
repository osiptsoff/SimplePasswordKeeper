package ru.simplepasswordkeeper.gui.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public final class Prompt extends JPanel {
    private final Map<String, JTextField> values;

    private String message;
    private int textFieldLength;

    public Prompt() {
        this.setSize(150, 150);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        values = new HashMap<String, JTextField>();

        message = "";
        textFieldLength = 10;
    }

    public void addField(String key, boolean hidden) throws NullPointerException {
        if(key == null)
            throw new NullPointerException();
        JTextField textField;

        if(hidden)
            textField = new JPasswordField(textFieldLength);
        else
            textField = new JTextField(textFieldLength);

        values.put(key, textField);
        this.add(new JLabel(key));
        this.add(textField);
    }

    public void setTextFieldLength(int textFieldLength) {
        this.textFieldLength = textFieldLength;
    }

    public void setMessage(String message) throws NullPointerException {
        if(message == null)
            throw new NullPointerException();

        this.message = message;
    }

    public int showPrompt() {
        return JOptionPane.showConfirmDialog(null, this, message, JOptionPane.OK_CANCEL_OPTION);
    }

    public void clearFields() {
        values.values().forEach(field -> field.setText(""));
    }

    public String getValue(String key) {
        return values.get(key).getText();
    }
}
