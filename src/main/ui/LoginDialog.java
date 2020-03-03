package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog implements ActionListener {

    private JFrame parentFrame;
    private FileFinder fileFinder;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton createNewButton;
    private LoginListener loginListener;

    public LoginDialog(JFrame frame, Boolean modal) {
        parentFrame = frame;
        setModal(modal);
    }

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

    public void setupCreateButton(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        createNewButton = new JButton("Create New Database");
        createNewButton.addActionListener(this);
        add(createNewButton, constraints);
    }

    public void setupSubmitButton(GridBagConstraints constraints) {
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        submitButton = new JButton("   Login   ");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    public void setupPasswordField(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);
    }

    public void setupPasswordLabel(GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel, constraints);
    }

    public void setupFileFinderButton(GridBagConstraints constraints) {
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(fileFinder.findFileButton, constraints);
    }

    public void setupFileFinderPath(GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(fileFinder.filePath, constraints);
    }

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

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}