package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents the dialog window encountered by the user when starting the application
//Allows the user to either login with an existing database or create a new database
public class LoginDialog extends JDialog implements ActionListener {

    private JFrame parentFrame;
    private FileFinder fileFinder;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton createNewButton;
    private LoginListener loginListener;

    //EFFECTS: Sets up parent frame and modal of JDialog
    public LoginDialog(JFrame frame, Boolean modal) {
        parentFrame = frame;
        setModal(modal);
    }

    //MODIFIES: this
    //EFFECTS: Sets up layout of dialog
    public void setup() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        fileFinder = new FileFinder();

        setupFileFinderLabel(constraints);

        setupFileFinderPath(constraints);

        setupFileFinderButton(constraints);

        setupPasswordLabel(constraints);

        setupPasswordField(constraints);

        setupSubmitButton(constraints);

        setupCreateButton(constraints);

        setSize(450, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places create new database button on dialog
    public void setupCreateButton(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        createNewButton = new JButton("Create New Database");
        createNewButton.addActionListener(this);
        add(createNewButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places submit button on dialog
    public void setupSubmitButton(GridBagConstraints constraints) {
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        submitButton = new JButton("   Login   ");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places password field on dialog
    public void setupPasswordField(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places password label on dialog
    public void setupPasswordLabel(GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places find file button on dialog
    public void setupFileFinderButton(GridBagConstraints constraints) {
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(fileFinder.findFileButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places find file text field on dialog
    public void setupFileFinderPath(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(fileFinder.filePath, constraints);
    }

    //MODIFIES: this
    //EFFECTS: Creates and places find file label on dialog
    public void setupFileFinderLabel(GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(fileFinder.filePathLabel, constraints);
    }

    @Override
    //MODIFIES: this
    //EFFECTS: Handles user click events for each of the buttons
    //         For the submit button, verifies user file and password and switches frames if user input valid
    //         For create new Button communicates event with MainFrame which routes to CreationDialog class.
    //         Otherwise creates JOption error window specifying user mistake
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            if (loginListener != null) {
                if (loginListener.loadDatabaseAndVerify(fileFinder.filePath.getText(), passwordField.getPassword())) {
                    setVisible(false);
                    parentFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect File or Password.");
                }
            }
        } else if (actionEvent.getSource().equals(createNewButton)) {
            loginListener.dialogSwitcher();
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets loginListener
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}