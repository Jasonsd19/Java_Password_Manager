package ui;

import model.Entry;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class EntryTableModel extends AbstractTableModel {

    private ArrayList<Entry> entries;
    static final String[] COLUMN_NAMES = {"Name", "Username", "Password"};

    public void setData(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Entry entry = entries.get(row);
        switch (column) {
            case 0:
                return entry.entryName;
            case 1:
                return entry.userName;
            case 2:
                return passwordToAsterisk(entry.getPassword());
        }
        return null;
    }

    public String passwordToAsterisk(String password) {
        return "*".repeat(password.length());
    }
}