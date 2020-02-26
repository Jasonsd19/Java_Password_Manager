package model;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// A password database that can add/remove entries and write data to text files.
public class Database {
    public String path;
    public String databaseName;
    public Cipher cipher;
    public Reader reader;
    public Writer writer;
    public ArrayList<Entry> entries;

    //EFFECTS: Create a new Database file with given name and master password.
    public Database(String name, String password) throws IOException {
        cipher = new Cipher(password);
        entries = new ArrayList<>();
        this.databaseName = name;
        if (Files.exists(Paths.get("data\\" + this.databaseName + ".json"))) {
            throw new FileAlreadyExistsException("File with that name already exists.");
        }
        Files.createFile(Paths.get("data\\" + this.databaseName + ".json"));
        path = "data\\" + this.databaseName + ".json";
        reader = new Reader(path);
        writer = new Writer(path, cipher);
    }

    //EFFECTS: Loads an existing Database file given the absolute path, database name, and master password.
    public Database(String path, String name, String password) throws EncryptionOperationNotPossibleException,
            IOException {
        cipher = new Cipher(password);
        entries = new ArrayList<>();
        this.databaseName = name;
        this.path = path;
        reader = new Reader(this.path);
        writer = new Writer(this.path, cipher);
        load();
    }

    //MODIFIES: this
    //EFFECTS: Adds new entry and returns true if entry name is unique, returns false otherwise.
    public boolean addNewEntry(String name, String userName, String password) {
        if (isUnique(name)) {
            entries.add(new Entry(name, userName, password, cipher));
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: Returns true and removes entry from database file if it exists, false otherwise.
    public boolean removeEntry(String name) {
        if (!isUnique(name)) {
            entries.removeIf(entry -> entry.entryName.equals(name));
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: Checks if given entry name is already in use.
    public boolean isUnique(String name) {
        for (Entry entry : entries) {
            if (entry.entryName.equals(name)) {
                return false;
            }
        }
        return true;
    }

    //REQUIRES: Entry with entryName actually exists (can check with isUnique method)
    //EFFECTS: Returns entry with given entryName
    public Entry getEntry(String entryName) {
        Entry result = null;
        for (Entry entry: entries) {
            if (entry.entryName.equals(entryName)) {
                result = entry;
            }
        }
        return result;
    }

    //MODIFIES: Entry
    //EFFECTS: Changes the name of given entry to specified newEntryName
    public void editEntryName(Entry entry, String newEntryName) {
        entry.setEntryName(newEntryName);
    }

    //MODIFIES: Entry
    //EFFECTS: Changes the username of given entry to specified newUserName
    public void editEntryUserName(Entry entry, String newUserName) {
        entry.setUserName(newUserName);
    }

    //MODIFIES: Entry
    //EFFECTS: Changes the password of given entry to specified newPassword
    public void editEntryPassword(Entry entry, String newPassword) {
        entry.setPassword(newPassword);
    }

    //EFFECTS: Returns the username of the entry with the given entry name.
    public String getEntryUserName(String name) {
        for (Entry entry : entries) {
            if (entry.entryName.equals(name)) {
                return entry.userName;
            }
        }
        return "";
    }

    //EFFECTS: Decrypts and returns the password of the entry with the given entry name.
    public String getEntryPassword(String name) {
        for (Entry entry : entries) {
            if (entry.entryName.equals(name)) {
                return entry.getPassword();
            }
        }
        return "";
    }

    //MODIFIES: json file
    //EFFECTS: Encrypts all text in Database file using master password as key.
    public void save() throws IOException {
        writer.writeEntriesToFile(entries);
    }

    //MODIFIES: json file
    //EFFECTS: Decrypts all text in Database file using master password as key.
    public void load() throws EncryptionOperationNotPossibleException, IOException {
        writer.writeDecryptedTextToFile(cipher.decryptTextOrPassword(reader.readEncryptedFile()));
        loadEntries();
    }

    //MODIFIES: this
    //EFFECTS: Loads all entries from an existing Database file and adds them to the entries list.
    public void loadEntries() throws IOException {
        entries.addAll(reader.readEntries());
    }
}