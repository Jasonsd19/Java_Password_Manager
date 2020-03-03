package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseToolbar extends JPanel implements ActionListener {

    private JButton getPasswordButton;
    private JButton addEntryButton;
    private JButton editEntryButton;
    private JButton removeEntryButton;
    private ToolBarListener toolBarListener;

    public DatabaseToolbar() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        getPasswordButton = new JButton("Get Password");
        getPasswordButton.setEnabled(false);
        addEntryButton = new JButton("Add Entry");
        editEntryButton = new JButton("Edit Entry");
        editEntryButton.setEnabled(false);
        removeEntryButton = new JButton("Remove Entry");
        removeEntryButton.setEnabled(false);

        getPasswordButton.addActionListener(this);
        addEntryButton.addActionListener(this);
        editEntryButton.addActionListener(this);
        removeEntryButton.addActionListener(this);

        add(getPasswordButton);
        add(addEntryButton);
        add(editEntryButton);
        add(removeEntryButton);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addEntryButton)) {
            toolBarListener.buttonListener("a");
        } else if (toolBarListener.isRowSelected()) {
            if (actionEvent.getSource().equals(getPasswordButton)) {
                toolBarListener.buttonListener("g");
            } else if (actionEvent.getSource().equals(editEntryButton)) {
                toolBarListener.buttonListener("e");
            } else {
                toolBarListener.buttonListener("r");
            }
        } else {
            disableButtons();
        }
    }

    public void setToolBarListener(ToolBarListener toolBarListener) {
        this.toolBarListener = toolBarListener;
    }

    public void enableButtons() {
        getPasswordButton.setEnabled(true);
        editEntryButton.setEnabled(true);
        removeEntryButton.setEnabled(true);
    }

    public void disableButtons() {
        getPasswordButton.setEnabled(false);
        editEntryButton.setEnabled(false);
        removeEntryButton.setEnabled(false);
    }
}
