package ui;

import model.Entry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Represents the table at the center of the MainFrame which displays database entries
public class EntryTable extends JPanel {

    private JTable entryTable;
    private EntryTableModel entryTableModel;
    private EntryTableListener entryTableListener;

    //EFFECTS: Sets layout for EntryTable JPanel and adds JTable using EntryTableModel
    public EntryTable() {
        setLayout(new BorderLayout());

        entryTableModel = new EntryTableModel();
        entryTable = new JTable(entryTableModel);

        entryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entryTable.getSelectionModel().addListSelectionListener(listSelectionEvent ->
                entryTableListener.enableButtons());
        add(new JScrollPane(entryTable), BorderLayout.CENTER);

    }

    //MODIFIES: entryTable
    //EFFECTS: Sets the data used by the EntryTableModel
    public void setData(ArrayList<Entry> entries) {
        entryTableModel.setData(entries);
    }

    //MODIFIES: entryTable
    //EFFECTS: Refreshes the data in the EntryTableModel
    public void refresh() {
        entryTableModel.fireTableDataChanged();
    }

    //MODIFIES: this
    //EFFECTS: Sets the listener that communicates between this class and MainFrame
    public void setEntryTableListener(EntryTableListener entryTableListener) {
        this.entryTableListener = entryTableListener;
    }

    //EFFECTS: Returns the entry name of the specified row
    public String getSelectedRowEntryName() {
        return (String) entryTable.getValueAt(entryTable.getSelectedRow(), 0);
    }

    //EFFECTS: Returns the position of the specified row
    public int getSelectedRow() {
        return entryTable.getSelectedRow();
    }
}
