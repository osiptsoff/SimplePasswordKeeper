package ru.simplepasswordkeeper.gui.toolbar;

import ru.simplepasswordkeeper.gui.Constants;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class Toolbar extends JPanel {
    protected JButton createButton,
            removeButton,
            editButton,
            helpButton;

    public Toolbar() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        createButton = new JButton("+");
        removeButton = new JButton(("-"));
        editButton = new JButton("E");
        helpButton = new JButton("?");

        createButton.setToolTipText("Добавить");
        removeButton.setToolTipText("Удалить");
        editButton.setToolTipText("Редактировать");
        helpButton.setToolTipText("Справка");

        createButton.setFont(Constants.buttonFont);
        removeButton.setFont(Constants.buttonFont);
        editButton.setFont(Constants.buttonFont);
        helpButton.setFont(Constants.buttonFont);

        createButton.addActionListener(e -> this.create());
        removeButton.addActionListener(e -> this.remove());
        editButton.addActionListener(e -> this.edit());
        helpButton.addActionListener(e -> this.help());

        this.add(createButton);
        this.add(removeButton);
        this.add(editButton);
        this.add(helpButton);
    }

    abstract void create();

    abstract void remove();

    abstract void edit();

    abstract void help();
}
