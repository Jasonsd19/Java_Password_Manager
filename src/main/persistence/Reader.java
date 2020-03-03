package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Entry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Represents a reader that can read entries from a specified Database text file
public class Reader {
    public String path;

    //EFFECTS: Constructs reader and instantiates field variables.
    public Reader(String path) {
        this.path = path;
    }

    //EFFECTS: Reads a decrypted Database text file and returns a list of all entries found within the file.
    public ArrayList<Entry> readEntries(String jsonString) throws IOException {
//        JsonReader reader = new JsonReader(new FileReader(path));
        return new Gson().fromJson(jsonString, new TypeToken<ArrayList<Entry>>() {
        }.getType());
    }

    //EFFECTS: Loads encrypted text from database file as string.
    public String readEncryptedFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        return bufferedReader.readLine();
    }
}