package model;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

// A password database that can add/remove entries and write data to text files.
public class Database {
    public String path;
    public String databaseName;
    private Cipher cipher;
    public Reader reader;
    public Writer writer;
    public ArrayList<Entry> entries;

    //EFFECTS: Create a new Database file with given name and master password.
    //IOException an exception raised if file is not found or error in reading/writing file
    public Database(String name, String password) throws IOException {
        cipher = new Cipher(password);
        entries = new ArrayList<>();
        this.databaseName = name;
        if (Files.exists(Paths.get("data\\" + this.databaseName + ".txt"))) {
            throw new FileAlreadyExistsException("data\\" + this.databaseName + ".txt");
        }
        File file = new File("data\\" + this.databaseName + ".txt");
        path = file.getPath();
        reader = new Reader(path, cipher);
        writer = new Writer(path, cipher);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("Entry Name, Username, Password,");
        writer.close();
    }

    //EFFECTS: Loads an existing Database file given the absolute path, database name, and master password.
    //EncryptionOperationNotPossibleException an exception raised when given incorrect password
    //IOException an exception raised if file is not found or error in reading/writing file
    public Database(String path, String name, String password) throws EncryptionOperationNotPossibleException,
            IOException {
        cipher = new Cipher(password);
        entries = new ArrayList<>();
        this.databaseName = name;
        this.path = path;
        reader = new Reader(this.path, cipher);
        writer = new Writer(this.path, cipher);
        load();
    }

    //MODIFIES: this, text file
    //EFFECTS: Adds new entry and returns true if entry name is unique, returns false otherwise.
    public boolean addNewEntry(String name, String userName, String password) throws IOException {
        if (isUnique(name)) {
            entries.add(writer.writeEntry(name, userName, password));
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this, text file
    //EFFECTS: Returns true and removes entry from database file if it exists, false otherwise.
    public boolean removeEntry(String name) throws IOException {
        if (!isUnique(name)) {
            reader.removeEntry(name);
            Iterator<Entry> iterator = entries.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().entryName.equals(name)) {
                    iterator.remove();
                }
            }
            return true;
        } else {
            return false;
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
    public void loadEntries() throws IOException {
        entries.addAll(reader.readEntries());
    }

    //EFFECTS: Returns the username of the entry with the given entry name.
    public String getEntryUserName(String name) {
        for (Entry entry: entries) {
            if (entry.entryName.equals(name)) {
                return entry.userName;
            }
        }
        return "";
    }

    //EFFECTS: Decrypts and returns the password of the entry with the given entry name.
    public String getEntryPassword(String name) {
        for (Entry entry: entries) {
            if (entry.entryName.equals(name)) {
                return entry.getPassword();
            }
        }
        return "";
    }

    //MODIFIES: text file
    //EFFECTS: Encrypts all text in Database file using master password as key.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void save() throws IOException {
        String encryptedText = cipher.encryptText(path);
        PrintWriter writer = new PrintWriter(new FileWriter(path));
        writer.print(encryptedText);
        writer.close();
    }

    //MODIFIES: text file
    //EFFECTS: Decrypts all text in Database file using master password as key.
    public void load() throws EncryptionOperationNotPossibleException, IOException {
        String decryptedText = cipher.decryptText(path);
        PrintWriter writer = new PrintWriter(new FileWriter(path));
        writer.print(decryptedText);
        writer.close();
        loadEntries();
    }
}