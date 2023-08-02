package ru.simplepasswordkeeper.gui.component;

import org.springframework.stereotype.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

@Component
public class MapTable extends JScrollPane {
    private static final String keyColumnHeader = "Ключ",
            valueColumnHeader = "Значение";

    private final JTable table;
    private final DefaultTableModel tableModel;

    public MapTable() {
        this(new JTable(new DefaultTableModel(new String[] {keyColumnHeader, valueColumnHeader}, 1)));
    }

    private MapTable(JTable table) {
        super(table);

        this.table = table;
        table.getTableHeader().setReorderingAllowed(false);
        tableModel = (DefaultTableModel) table.getModel();
    }

    public void clearAndFill(Map<String, String> map) {
        this.clear();

        map.entrySet().forEach(entry -> tableModel.addRow(new String[] {entry.getKey(), entry.getValue() }));
    }

    public void clear() {
        for(int i = 0, size = tableModel.getRowCount(); i < size; i++)
            tableModel.removeRow(0);
    }

    public void addEmptyRow() {
        tableModel.addRow((Vector<?>) null);
    }

    public void removeRow(int row) throws IndexOutOfBoundsException {
        tableModel.removeRow(row);
    }

    public LinkedHashMap<String, String> collectMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        if(table.isEditing())
            table.getCellEditor().stopCellEditing();

        tableModel.getDataVector().stream()
                .filter(v -> v.get(0) != null && !v.get(0).toString().isBlank() && v.get(1) != null && !v.get(1).toString().isBlank())
                .forEach(v -> map.put(v.get(0).toString(), v.get(1).toString()));

        return map;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}
