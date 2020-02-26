package ui;

import model.Database;
import model.Entry;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

//NOTE: Cannot mask password fields because Console.readPassword() does not function properly in IDEs and
//      always results in NullPointerExceptions.
// Runs the Password Manager program
public class PassManagerApp {
    Scanner scanner;
    Database database;
    Console console;

    //EFFECTS: Starts the text-based user interface
    public PassManagerApp() {
        textUserInterface();
    }

    //MODIFIES: this
    //EFFECTS: Displays text interface and takes in user input
    public void textUserInterface() {
        scanner = new Scanner(System.in);
        console = System.console(); //NullPointerException if used in IDE, will utilise later on.
        while (true) {
            loadStartScreen();
            String input = scanner.nextLine();
            String inputLowercase = input.toLowerCase();
            if (inputLowercase.equals("e")) {
                break;
            } else {
                processStartInput(input);
            }
        }
    }

    //EFFECTS: Displays start menu and options
    public void loadStartScreen() {
        System.out.println("Welcome to the password manager.");
        System.out.println("Type n - to create a new database");
        System.out.println("Type l - to load a database");
        System.out.println("Type e - to exit the program");
    }

    //EFFECTS: Initiates correct method depending on user input.
    public void processStartInput(String input) {
        if (input.equals("n")) {
            newDatabase();
        } else if (input.equals("l")) {
            loadDatabase();
        } else {
            System.out.println("Invalid Input!");
        }
    }

    //MODIFIES: this
    //EFFECTS: Takes in input to instantiate a new Database fil for user, also displays
    //         options to modify/utilise the constructed Database file
    //IOException an exception raised if file is not found or error in reading/writing file
    public void newDatabase() {
        try {
            System.out.println("Please enter the name of the new database.");
            String dbName = scanner.nextLine();
            System.out.println("Please enter the master password for the database.");
            String password = scanner.nextLine();
            database = new Database(dbName, password);
            while (true) {
                loadMainScreen();
                String input = scanner.nextLine();
                String inputLowercase = input.toLowerCase();
                if (inputLowercase.equals("s")) {
                    saveDatabase();
                    System.exit(0);
                } else {
                    processMainInput(input);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to create database, please try again with a different name.");
        }
    }

    //MODIFIES: this
    //EFFECTS: Takes in user inputted information for a pre-existing Database file, decrypts
    //         the file, and loads in the Database file into the program, also displays options to
    //         modify/utilise the Database file.
    //EncryptionOperationNotPossibleException an exception raised when given incorrect password
    //IOException an exception raised if file is not found or error in reading/writing file
    public void loadDatabase() {
        try {
            System.out.println("Please enter the absolute path to the database file.");
            String absPath = scanner.nextLine();
            System.out.println("Please enter the name of the database.");
            String oldDatabaseName = scanner.nextLine();
            System.out.println("Please enter the master password for the database.");
            String masterPassword = scanner.nextLine();
            database = new Database(absPath, oldDatabaseName, masterPassword);
            while (true) {
                loadMainScreen();
                String input = scanner.nextLine();
                String inputLowercase = input.toLowerCase();
                if (inputLowercase.equals("s")) {
                    saveDatabase();
                } else {
                    processMainInput(input);
                }
            }
        } catch (EncryptionOperationNotPossibleException e) {
            System.out.println("Incorrect Password.");
        } catch (IOException e) {
            System.out.println("Unable to find file.");
        }
    }

    //EFFECTS: Displays main menu containing possible functions of the Database file.
    public void loadMainScreen() {
        System.out.println("Type a - to add an entry to the database");
        System.out.println("Type e - to edit an existing entry");
        System.out.println("Type r - to remove an entry from the database");
        System.out.println("Type g - to get an entry from the database");
        System.out.println("Type s - to save and exit");
    }

    //EFFECTS: Initiates correct method depending on user input.
    public void processMainInput(String input) {
        switch (input) {
            case "a":
                newEntry();
                break;
            case "e":
                editEntry();
                break;
            case "g":
                getEntry();
                break;
            case "r":
                removeEntry();
                break;
            default:
                System.out.println("Invalid Input!");
                break;
        }
    }

    //MODIFIES: database
    //EFFECTS: Allows user to edit an existing entry's name, username, or password
    public void editEntry() {
        System.out.println("Please enter the name of the entry.");
        String entryName = scanner.nextLine();
        if (!database.isUnique(entryName)) {
            Entry entryToEdit = database.getEntry(entryName);
            loadEditScreen();
            String option = scanner.nextLine();
            processEditInput(entryToEdit, option.toLowerCase());
        } else {
            System.out.println("Entry not in database.");
        }
    }

    //EFFECTS: Displays options for editing and existing entry
    public void loadEditScreen() {
        System.out.println("Type n - to edit the name of the entry");
        System.out.println("Type u - to edit the username associated with the entry");
        System.out.println("Type p - to edit the password associated with the entry");
    }

    //EFFECTS: Calls correct method depending on user input
    public void processEditInput(Entry entry, String entryName) {
        switch (entryName) {
            case "n":
                editEntryName(entry);
                break;
            case "u":
                editEntryUserName(entry);
                break;
            case "p":
                editEntryPassword(entry);
                break;
            default:
                System.out.println("Invalid Input!");
        }

    }

    //EFFECTS: Changes the name of the entry to the name specified by the user
    public void editEntryName(Entry entry) {
        System.out.println("Please enter the new name of the entry.");
        String entryName = scanner.nextLine();
        database.editEntryName(entry, entryName);
        System.out.println("Entry name successfully changed!");
    }

    //EFFECTS: Changes the username of the entry to the username specified by the user
    public void editEntryUserName(Entry entry) {
        System.out.println("Please enter the new username of the entry.");
        String entryName = scanner.nextLine();
        database.editEntryUserName(entry, entryName);
        System.out.println("Entry username successfully changed!");
    }

    //EFFECTS: Changes the password of the entry to the password specified by the user
    public void editEntryPassword(Entry entry) {
        System.out.println("Please enter the new password of the entry.");
        String entryName = scanner.nextLine();
        database.editEntryPassword(entry, entryName);
        System.out.println("Entry password successfully changed!");
    }

    //MODIFIES: database
    //EFFECTS: Takes information from user and uses it to create a new entry
    //         for the user's Database file
    //IOException an exception raised if file is not found or error in reading/writing file
    public void newEntry() {
        System.out.println("Please enter the name of the entry.");
        String entryName = scanner.nextLine();
        System.out.println("Please enter the username.");
        String userName = scanner.nextLine();
        System.out.println("Please enter the password.");
        String entryPassword = scanner.nextLine();
        if (database.addNewEntry(entryName, userName, entryPassword)) {
            System.out.println("New entry added.");
        } else {
            System.out.println("Entry name not unique.");
        }
    }


    //MODIFIES: database
    //EFFECTS: Prints the username and password of an entry given by the user,
    //         if entry doesn't exist prints a message.
    public void getEntry() {
        System.out.println("Please enter the name of the entry you wish to retrieve.");
        String entryGet = scanner.nextLine();
        if (database.getEntryUserName(entryGet).equals("") | database.getEntryPassword(entryGet).equals("")) {
            System.out.println("Entry not in database.");
        } else {
            System.out.println("username: " + database.getEntryUserName(entryGet));
            System.out.println("password: " + database.getEntryPassword(entryGet));
        }
    }

    //MODIFIES: database
    //EFFECTS: Removes the entry specified by the user from the Database file.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void removeEntry() {
        System.out.println("Please enter the name of the entry you wish to remove.");
        String entryGet = scanner.nextLine();
        if (database.removeEntry(entryGet)) {
            System.out.println("Entry removed.");
        } else {
            System.out.println("Entry does not exist.");
        }
    }

    //MODIFIES: database
    //EFFECTS: Saves and encrypts all the data in the Database and entries to a text file.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void saveDatabase() {
        try {
            database.save();
            System.out.println("Saved database.");
        } catch (IOException e) {
            System.out.println("File error, shutting down.");
        } finally {
            System.exit(0);
        }
    }
}
