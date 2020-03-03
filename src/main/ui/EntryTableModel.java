package ui;

import model.Entry;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

//Represents the model for the EntryTable class
public class EntryTableModel extends AbstractTableModel {

    private ArrayList<Entry> entries;
    static final String[] COLUMN_NAMES = {"Name", "Username", "Password"};

    //MODIFIES: this
    //EFFECTS: Sets the data that the table will display
    public void setData(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    //EFFECTS: Returns the name of specified column
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    //EFFECTS: Returns the number of rows the table will display
    public int getRowCount() {
        return entries.size();
    }

    @Override
    //EFFECTS: Returns the number of columns the table will display
    public int getColumnCount() {
        return 3;
    }

    @Override
    //EFFECTS: Returns the value of the table at the specified row and column
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

    //EFFECTS: Converts given password string to a string of asterisks of equivalent length.
    public String passwordToAsterisk(String password) {
        return "*".repeat(password.length());
    }
}