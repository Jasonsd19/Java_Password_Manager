package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents the tool bar with buttons that modify entries on the top of the MainFrame
public class DatabaseToolbar extends JPanel implements ActionListener {

    private JButton getPasswordButton;
    private JButton addEntryButton;
    private JButton editEntryButton;
    private JButton removeEntryButton;
    private ToolBarListener toolBarListener;

    //EFFECTS: Sets up the layout of the toolbar JPanel and adds buttons
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
    //EFFECTS: Communicates the pressing of any of the specified buttons or selecting of a specific row in the table
    //         with the MainFrame, which carries out the correct response.
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

    //MODIFIES: this
    //EFFECTS: Sets the listener that communicates between the DatabaseToolbar and MainFrame classes
    public void setToolBarListener(ToolBarListener toolBarListener) {
        this.toolBarListener = toolBarListener;
    }

    //MODIFIES: this
    //EFFECTS: Enables all buttons except for the addEntryButton
    public void enableButtons() {
        getPasswordButton.setEnabled(true);
        editEntryButton.setEnabled(true);
        removeEntryButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: Disables all buttons except for the addEntryButton
    public void disableButtons() {
        getPasswordButton.setEnabled(false);
        editEntryButton.setEnabled(false);
        removeEntryButton.setEnabled(false);
    }
}
