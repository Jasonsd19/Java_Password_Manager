package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents the dialog window encountered by the user when creating a new database
public class CreationDialog extends JDialog implements ActionListener {

    private JFrame parentFrame;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton backButton;
    private JButton createButton;
    private CreationListener creationListener;

    //EFFECTS: Sets up parent frame and modal of JDialog
    public CreationDialog(JFrame frame, Boolean modal) {
        parentFrame = frame;
        setModal(modal);
    }

    //MODIFIES: this
    //EFFECTS: Sets up layout of dialog
    public void setup() {
        setVisible(false);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets standardInset = new Insets(5, 5, 5, 5);

        setupNameLabel(constraints, standardInset);

        setupNameField(constraints, standardInset);

        setupPasswordLabel(constraints, standardInset);

        setupPasswordField(constraints, standardInset);

        setupBackButton(constraints, standardInset);

        setupCreateButton(constraints, standardInset);

        setSize(325, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the name label on the dialog
    public void setupNameLabel(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the name text field on the dialog
    public void setupNameField(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        nameField = new JTextField(20);
        add(nameField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the password label on the dialog
    public void setupPasswordLabel(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the password text field on the dialog
    public void setupPasswordField(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the create button on the dialog
    public void setupCreateButton(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        createButton = new JButton("Create");
        createButton.addActionListener(this);
        add(createButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places the back button on the dialog
    public void setupBackButton(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Sets creationListener that communicates with the MainFrame
    public void setCreationListener(CreationListener creationListener) {
        this.creationListener = creationListener;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: Handles user click events for the create button
    //         Create button verifies user entered information is valid then creates database and switches to main frame
    //         Otherwise provides JOption error windows specifying mistake made by user.
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(createButton)) {
            if (creationListener != null && passwordField.getPassword().length >= 8) {
                if (creationListener.createNewAndVerify(nameField.getText(), passwordField.getPassword())) {
                    setVisible(false);
                    parentFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Database with that name already exists!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password needs to be at least 8"
                        + " characters long!");
            }
        } else if (actionEvent.getSource().equals(backButton)) {
            creationListener.changeDialog();
        }
    }
}