package ui;

import model.Entry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EntryTable extends JPanel {

    private JTable entryTable;
    private EntryTableModel entryTableModel;
    private EntryTableListener entryTableListener;

    public EntryTable() {
        setLayout(new BorderLayout());

        entryTableModel = new EntryTableModel();
        entryTable = new JTable(entryTableModel);

        entryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entryTable.getSelectionModel().addListSelectionListener(listSelectionEvent ->
                entryTableListener.enableButtons());
        add(new JScrollPane(entryTable), BorderLayout.CENTER);

    }

    public void setData(ArrayList<Entry> entries) {
        entryTableModel.setData(entries);
    }

    public void refresh() {
        entryTableModel.fireTableDataChanged();
    }

    public void setEntryTableListener(EntryTableListener entryTableListener) {
        this.entryTableListener = entryTableListener;
    }

    public String getSelectedRowEntryName() {
        return (String) entryTable.getValueAt(entryTable.getSelectedRow(), 0);
    }

    public int getSelectedRow() {
        return entryTable.getSelectedRow();
    }
}
