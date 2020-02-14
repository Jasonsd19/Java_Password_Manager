package persistence;

import model.Cipher;
import model.Entry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Represents a reader that can read/remove/manage entries from Database text file
public class Reader {
    public String path;
    private Cipher cipher;

    //EFFECTS: Constructs reader and instantiates field variables.
    public Reader(String path, Cipher cipher) {
        this.path = path;
        this.cipher = cipher;
    }

    //EFFECTS: Reads a decrypted Database text file and returns a list of all entries found within the file.
    //IOException an exception raised if file is not found or error in reading/writing file
    //NoSuchElementException an exception raised once reader reaches end of file.
    public ArrayList<Entry> readEntries() {
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
        } catch (IOException e) {
            System.out.println("Can't Find File.");
        } catch (NoSuchElementException e) {
            System.out.println("Loaded Entries.");
        }
        return results;
    }

    //MODIFIES: text file
    //EFFECTS: Removes entry with given entry name from Database text file.
    //IOException an exception raised if file is not found or error in reading/writing file
    public void removeEntry(String name) {
        try {
            File mainFile = new File(path);
            File tempFile = new File(path + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            PrintWriter printWriter = new PrintWriter(new FileWriter(new File(path + ".txt")));
            String textLine;
            while ((textLine = bufferedReader.readLine()) != null) {
                if (!textLine.contains(name)) {
                    printWriter.println(textLine);
                    printWriter.flush();
                }
            }
            printWriter.close();
            bufferedReader.close();
            replaceContents(mainFile, tempFile);
            tempFile.delete();
        } catch (IOException e) {
            System.out.println("File Does Not Exist.");
        }
    }

    public void replaceContents(File mainFile, File tempFile) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(tempFile.getPath())), StandardCharsets.UTF_8);
        PrintWriter printWriter = new PrintWriter(mainFile);
        printWriter.print(text);
        printWriter.close();
    }

}

