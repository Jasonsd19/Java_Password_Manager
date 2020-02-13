package ui;

import model.Database;
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
    public PassManagerApp() throws IOException {
        textUserInterface();
    }

    //MODIFIES: this
    //EFFECTS: Displays text interface and takes in user input
    public void textUserInterface() throws IOException {
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
    public void processStartInput(String input) throws IOException {
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
    public void newDatabase() throws IOException {
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
    }

    //MODIFIES: this
    //EFFECTS: Takes in user inputted information for a pre-existing Database file, decrypts
    //         the file, and loads in the Database file into the program, also displays options to
    //         modify/utilise the Database file.
    //EncryptionOperationNotPossibleException an exception raised when given incorrect password
    public void loadDatabase() throws IOException {
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
                    System.exit(0);
                } else {
                    processMainInput(input);
                }
            }
        } catch (EncryptionOperationNotPossibleException e) {
            System.out.println("Incorrect Password.");
        }
    }

    //EFFECTS: Displays main menu containing possible functions of the Database file.
    public void loadMainScreen() {
        System.out.println("Type e - to add an entry to the database");
        System.out.println("Type r - to remove an entry from the database");
        System.out.println("Type g - to get an entry from the database");
        System.out.println("Type s - to save and exit");
    }

    //EFFECTS: Initiates correct method depending on user input.
    public void processMainInput(String input) throws IOException {
        switch (input) {
            case "e":
                newEntry();
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

    //MODIFIES: this
    //EFFECTS: Takes information from user and uses it to create a new entry
    //         for the user's Database file
    public void newEntry() throws IOException {
        System.out.println("Please enter the name of the entry.");
        String entryName = scanner.nextLine();
        System.out.println("Please enter the username.");
        String userName = scanner.nextLine();
        System.out.println("Please enter the password.");
        String entryPassword = scanner.nextLine();
        database.addNewEntry(entryName, userName, entryPassword);
    }

    //MODIFIES: this
    //EFFECTS: Returns the username and password of an entry given by the user,
    //         if entry doesn't exist returns a message.
    public void getEntry() {
        System.out.println("Please enter the name of the entry you wish to retrieve.");
        String entryGet = scanner.nextLine();
        database.getEntryUserName(entryGet);
        database.getEntryPassword(entryGet);
    }

    //MODIFIES: this
    //EFFECTS: Removes the entry specified by the user from the Database file.
    public void removeEntry() throws IOException {
        System.out.println("Please enter the name of the entry you wish to remove.");
        String entryGet = scanner.nextLine();
        database.removeEntry(entryGet);
    }

    //MODIFIES: this
    //EFFECTS: Saves and encrypts all the data in the Database and entries to a text file.
    public void saveDatabase() {
        database.save();
    }
}
