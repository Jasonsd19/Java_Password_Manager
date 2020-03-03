package ui;

import model.Database;
import model.Entry;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;

// Represents the Main Frame of the application that displays and manipulates entries
// Also integrates various dialogs and acts as the center of communication between
// All other dialogs, frames and instances.
public class MainFrame extends JFrame {

    private Database database;
    private LoginDialog loginDialog;
    private CreationDialog creationDialog;
    private EntryTable entryTable;

    // EFFECTS: Sets up various JDialogs and displays database entries if login is successful
    public MainFrame() {
        this.setVisible(false);

        creationDialog = new CreationDialog(this, true);
        creationDialog.setCreationListener(this::createDatabase);

        loginDialog = new LoginDialog(this, true);
        loginDialog.setLoginListener(new LoginListener() {
            @Override
            public Boolean loadDatabaseAndVerify(String path, char[] password) {
                return loadDatabase(path, "temp", password);
            }

            @Override
            public void dialogSwitcher() {
                changeDialog();
            }
        });

        loginDialog.setup();

        DatabaseToolbar databaseToolbar = getDatabaseToolbar();
        setupEntryTable(databaseToolbar);

        if (isVisible()) {
            setupMainFrame(databaseToolbar);
        } else {
            this.dispose();
            System.exit(0);
        }
    }

    // MODIFIES: this
    //EFFECTS: Sets up entryTable frame that displays entries
    public void setupEntryTable(DatabaseToolbar databaseToolbar) {
        entryTable = new EntryTable();
        entryTable.setEntryTableListener(databaseToolbar::enableButtons);
    }

    //EFFECTS: Sets up database Toolbar with buttons to manipulate entries
    public DatabaseToolbar getDatabaseToolbar() {
        DatabaseToolbar databaseToolbar = new DatabaseToolbar();
        databaseToolbar.setToolBarListener(new ToolBarListener() {
            public void buttonListener(String buttonOperation) {
                switch (buttonOperation) {
                    case "g":
                        getEntryPassword();
                        break;
                    case "a":
                        addEntry();
                        break;
                    case "e":
                        editEntry();
                        break;
                    default:
                        removeEntry();
                        break;
                    }
            }

            public Boolean isRowSelected() {
                return entryTable.getSelectedRow() != -1;
            }
        });
        return databaseToolbar;
    }

    // EFFECTS: Sets up border layout, menu, toolbar and table
    public void setupMainFrame(DatabaseToolbar databaseToolbar) {
        setLayout(new BorderLayout());

        setJMenuBar(makeMenuBar());

        entryTable.setData(database.entries);

        add(databaseToolbar, BorderLayout.NORTH);
        add(entryTable, BorderLayout.CENTER);

        setupWindowListener();

        this.setMinimumSize(new Dimension(575, 500));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // EFFECTS: Brings up save dialog when user attempts to exit application
    public void setupWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Save this file before exiting?", "Save File",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    try {
                        database.save();
                        dispose();
                        System.exit(0);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving file!");
                    }
                } else if (choice == JOptionPane.NO_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Takes user input from LoginDialog, verifies if it's correct and loads relevant database if correct
    public Boolean loadDatabase(String path, String name, char[] password) {
        try {
            database = new Database(path, name, String.valueOf(password));
            return true;
        } catch (IOException | EncryptionOperationNotPossibleException e) {
            //can't find or open file
            //not likely to happen as they choose file
            //wrong password likely
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Takes user input from CreationDialog, verifies if it's valid and creates database if valid
    public Boolean createDatabase(String name, char[] password) {
        try {
            database = new Database(name, String.valueOf(password));
            return true;
        } catch (IOException e) {
            //File already exists
            return false;
        }
    }

    // MODIFIES: loginDialog
    // EFFECTS: Closes loginDialog and brings up creationDialog
    public void changeDialog() {
        loginDialog.setVisible(false);
        creationDialog.setup();
    }

    // EFFECTS: Creates menu bar for main frame
    private JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(actionEvent -> {
            try {
                database.save();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to save file.");
            }
        });
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> {
            dispose();
            System.exit(0);
        });

        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    // MODIFIES: database, entryTable
    // EFFECTS: Adds entry to entryTable and database
    public void addEntry() {
        JTextField entryNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Entry Name:", entryNameField,
                "Username:", usernameField,
                "Password:", passwordField,
        };
        int choice = JOptionPane.showConfirmDialog(null, message, "Add Entry",
                JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION) {
            String entryName = entryNameField.getText();
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            database.addNewEntry(entryName, username, password);
            entryTable.refresh();
        }
    }

    // MODIFIES: database, entryTable
    // EFFECTS: Edits entry in Database and displays edited entry in JTable
    public void editEntry() {
        Entry entryToEdit = null;
        String[] options = {
                "Edit Username",
                "Edit Password",
                "Cancel"
        };
        String entryName = entryTable.getSelectedRowEntryName();
        if (!database.isUnique(entryName)) {
            entryToEdit = database.getEntry(entryName);
        } else {
            JOptionPane.showMessageDialog(null, "Entry doesn't exist!");
        }
        int option = JOptionPane.showOptionDialog(null, "Edit Entry",
                "Choose what to edit", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (option == 0) {
            changeUsername(entryToEdit);
        } else if (option == 1) {
            changePassword(entryToEdit);
        }
    }

    // MODIFIES: database, entryTable
    // EFFECTS: Changes current username to specified username
    public void changeUsername(Entry entry) {
        JTextField usernameField = new JTextField();
        Object[] message = {
                "New Username:", usernameField
        };
        int choice = JOptionPane.showConfirmDialog(null, message, "Edit Entry",
                JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION) {
            database.editEntryUserName(entry, usernameField.getText());
            entryTable.refresh();
        }
    }

    // MODIFIES: database, entryTable
    // EFFECTS: Changes current password to specified password
    public void changePassword(Entry entry) {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "New Password:", passwordField
        };
        int choice = JOptionPane.showConfirmDialog(null, message, "Edit Entry",
                JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION) {
            database.editEntryPassword(entry, String.valueOf(passwordField.getPassword()));
            entryTable.refresh();
        }
    }

    // MODIFIES: database, entryTable
    // EFFECTS: Removes specified entry from database and entryTable
    public void removeEntry() {
        String entryName = entryTable.getSelectedRowEntryName();
        if (!database.isUnique(entryName)) {
            database.removeEntry(entryName);
            entryTable.refresh();
        } else {
            JOptionPane.showMessageDialog(null, "Entry doesn't exist!");
        }
    }

    // EFFECTS: Retrieves password of specified entry and copies it to clipboard.
    public void getEntryPassword() {
        String entryName = entryTable.getSelectedRowEntryName();
        if (!database.isUnique(entryName)) {
            StringSelection stringSelection = new StringSelection(database.getEntryPassword(entryName));
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            JOptionPane.showMessageDialog(null, "Password copied to clipboard!");
        } else {
            JOptionPane.showMessageDialog(null, "Entry doesn't exist!");
        }
    }
}
