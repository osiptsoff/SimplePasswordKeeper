package ru.simplepasswordkeeper.gui.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.gui.Constants;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;

@Component
@Scope("prototype")
public class ScrollableList<T> extends JScrollPane {
    protected JList<T> list;
    protected DefaultListModel<T> model;

    public ScrollableList() { this(new JList<T>()); }

    private ScrollableList(JList<T> list) {
        super(list);
        list.setBorder(new LineBorder(Color.BLACK));
        this.list = list;
        list.setFont(Constants.listFont);

        model = new DefaultListModel<>();
        list.setModel(model);
    }

    public void addElement(T obj) {
        model.addElement(obj);
    }

    public void removeElement(T obj) {
        model.removeElement(obj);
    }

    public void setFixedCellHeight(int cellHeight) { list.setFixedCellHeight(cellHeight); }

    public T getSelectedElement() { return list.getSelectedValue(); }

    public void clear() { model.clear(); }

    public boolean contains(T obj) {
        return model.contains(obj);
    }
}
