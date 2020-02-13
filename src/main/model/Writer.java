package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//Represents a writer that can write entries to a Database text file
public class Writer {
    String path;
    Cipher cipher;

    //EFFECTS: Constructs a writer and instantiates field variables.
    public Writer(String path, Cipher cipher) {
        this.path = path;
        this.cipher = cipher;
    }

    //MODIFIES: text file
    //EFFECTS: Creates an Entry, writes the formatted entry to the Database text file,
    //         and returns the created Entry.
    //IOException an exception raised if file is not found or error in reading/writing file
    public Entry writeEntry(String name, String userName, String password) throws IOException {
        Entry newEntry = new Entry(name, userName, password, cipher);
        String formattedEntry = newEntry.formattedEntry();
        PrintWriter writer = new PrintWriter(new FileWriter(path, true));
        writer.println(formattedEntry);
        writer.close();
        return newEntry;
    }
}
