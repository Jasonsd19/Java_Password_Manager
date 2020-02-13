package model;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Represents a reader that can read/remove/manage entries from Database text file
public class Reader {
    String path;
    Cipher cipher;

    //EFFECTS: Constructs reader and instantiates field variables.
    public Reader(String path, Cipher cipher) {
        this.path = path;
        this.cipher = cipher;
    }

    //EFFECTS: Reads a decrypted Database text file and returns a list of all entries found within the file.
    //IOException an exception raised if file is not found or error in reading/writing file
    //NoSuchElementException an exception raised once reader reaches end of file.
    public ArrayList<Entry> readEntries() throws IOException {
        ArrayList<Entry> results = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(path));
            reader.useDelimiter(",");
            while (reader.hasNext()) {
                String name = reader.next();
                String strippedName = name.replaceAll("^\r\n+", "");
                String userName = reader.next();
                String password = reader.next();
                Entry loadedEntry = new Entry(strippedName, userName, password, cipher);
                if (loadedEntry.entryName.contains("Entry Name")) {
                    continue;
                } else {
                    results.add(loadedEntry);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Loaded Entries.");
        }
        return results;
    }

    //MODIFIES: text file
    //EFFECTS: Removes entry with given entry name from Database text file.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void removeEntry(String name) throws IOException {
        File inFile = new File(path);
        File outFile = new File(path + ".txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        PrintWriter printWriter = new PrintWriter(new FileWriter(outFile));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.contains(name)) {
                printWriter.println(line);
                printWriter.flush();
            }
        }
        printWriter.close();
        bufferedReader.close();
        inFile.delete();
        outFile.renameTo(inFile);
    }

}

