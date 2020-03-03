package persistence;

import com.google.gson.Gson;
import model.Cipher;
import model.Entry;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Represents a writer that can write entries to a specified Database text file
public class Writer {
    public String path;
    public Cipher cipher;

    //EFFECTS: Constructs a writer and instantiates field variables.
    public Writer(String path, Cipher cipher) {
        this.path = path;
        this.cipher = cipher;
    }

    //MODIFIES: json file
    //EFFECTS: Writes all entries in the given list to the Database text file.
    public void writeEntriesToFile(ArrayList<Entry> entries) throws IOException {
        Gson gson = new Gson();
        String decryptedText = gson.toJson(entries);
        String encryptedText = cipher.encryptTextOrPassword(decryptedText);
        PrintWriter writer = new PrintWriter(new FileWriter(path));
        writer.print(encryptedText);
        writer.close();
    }
}