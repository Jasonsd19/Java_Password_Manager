package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationDialog extends JDialog implements ActionListener {

    private JFrame parentFrame;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton createButton;
    private CreationListener creationListener;

    public CreationDialog(JFrame frame, Boolean modal) {
        parentFrame = frame;
        setModal(modal);
    }

    public void setup() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets standardInset = new Insets(5, 5, 5, 5);

        setupNameLabel(constraints, standardInset);

        setupNameField(constraints, standardInset);

        setupPasswordLabel(constraints, standardInset);

        setupPasswordField(constraints, standardInset);

        setupCreateButton(constraints, standardInset);

        setSize(325, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

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

    public void setupCreateButton(GridBagConstraints constraints, Insets standardInset) {
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.insets = standardInset;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        createButton = new JButton("Create");
        createButton.addActionListener(this);
        add(createButton, constraints);
    }

    public void setCreationListener(CreationListener creationListener) {
        this.creationListener = creationListener;
    }

    @Override
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
        }
    }
}