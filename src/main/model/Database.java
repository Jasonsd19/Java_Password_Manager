package model;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

// A password database that can add/remove entries and write data to text files.
public class Database {
    public String path;
    String databaseName;
    private Cipher cipher;
    public Reader reader;
    public Writer writer;
    public ArrayList<Entry> entries;

    //EFFECTS: Create a new Database file with given name and master password.
    //IOException an exception raised if file is not found or error in reading/writing file
    public Database(String name, String password) {
        try {
            cipher = new Cipher(password);
            entries = new ArrayList<>();
            this.databaseName = name;
            File file = new File("data\\" + this.databaseName + ".txt");
            path = file.getPath();
            reader = new Reader(path, cipher);
            writer = new Writer(path, cipher);
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println("Entry Name, Username, Password,");
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't Find File.");
        }
    }

    //EFFECTS: Loads an existing Database file given the absolute path, database name, and master password.
    //EncryptionOperationNotPossibleException an exception raised when given incorrect password
    public Database(String path, String name, String password) throws EncryptionOperationNotPossibleException {
        cipher = new Cipher(password);
        entries = new ArrayList<>();
        this.databaseName = name;
        this.path = path;
        reader = new Reader(this.path, cipher);
        writer = new Writer(this.path, cipher);
        load();
    }

    //MODIFIES: this, text file
    //EFFECTS: Adds a new entry to the entries list, given a unique entry name, writes the entry to the
    //         Database file and finally add the entry to the entries list.
    public void addNewEntry(String name, String userName, String password) {
        if (isUnique(name)) {
            entries.add(writer.writeEntry(name, userName, password));
        } else {
            System.out.println("Entry name not unique.");
        }
    }

    //MODIFIES: this, text file
    //EFFECTS: Removes an entry from the entries list and the Database file.
    public void removeEntry(String name) {
        if (!isUnique(name)) {
            reader.removeEntry(name);
            Iterator<Entry> iterator = entries.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().entryName.equals(name)) {
                    iterator.remove();
                }
            }
        } else {
            System.out.println("Entry does not exist.");
        }
    }


    //EFFECTS: Checks if given entry name is already in use.
    public boolean isUnique(String name) {
        for (Entry entry: entries) {
            if (entry.entryName.equals(name)) {
                return false;
            }
        }
        return true;
    }

    //MODIFIES: this
    //EFFECTS: Loads all entries from an existing Database file and adds them to the entries list.
    public void loadEntries() {
        entries.addAll(reader.readEntries());
    }

    //EFFECTS: Returns the username of the entry with the given entry name.
    public String getEntryUserName(String name) {
        for (Entry entry: entries) {
            if (entry.entryName.equals(name)) {
                System.out.println("username: " + entry.userName);
                return entry.userName;
            }
        }
        System.out.println("Entry not in database.");
        return "";
    }

    //EFFECTS: Decrypts and returns the password of the entry with the given entry name.
    public String getEntryPassword(String name) {
        for (Entry entry: entries) {
            if (entry.entryName.equals(name)) {
                System.out.println("password: " + entry.getPassword());
                return entry.getPassword();
            }
        }
        System.out.println("Entry not in database.");
        return "";
    }

    //MODIFIES: text file
    //EFFECTS: Encrypts all text in Database file using master password as key.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void save() {
        try {
            String encryptedText = cipher.encryptText(path);
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            writer.print(encryptedText);
            writer.close();
            System.out.println("Saved database at " + path);
        } catch (IOException e) {
            System.out.println("Can't Find File.");
        }
    }

    //MODIFIES: text file
    //EFFECTS: Decrypts all text in Database file using master password as key.
    //IOException an exception raised if file is not found or error in reading/writing file
    //EncryptionOperationNotPossibleException an exception raised when given incorrect password
    public void load() throws EncryptionOperationNotPossibleException {
        try {
            String decryptedText = cipher.decryptText(path);
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            writer.print(decryptedText);
            writer.close();
            loadEntries();
        } catch (IOException e) {
            System.out.println("Can't Find File.");
        }
    }
}