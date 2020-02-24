package persistence;

import model.Database;
import model.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    Database databaseTest;

    //Both readEntries() and removeEntries() are tested indirectly in DatabaseTest.

    @BeforeEach
    public void setup() {
        try {
            if (Files.exists(Paths.get("data\\testReader.txt"))) {
                databaseTest = new Database("data\\testReader.txt", "testReader", "password");
                databaseTest.entries = new ArrayList<>();
                File file = new File("data\\" + databaseTest.databaseName + ".txt");
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.println("Entry Name, Username, Password,");
                writer.close();
            } else {
                databaseTest = new Database("testReader", "password");
            }
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void saveAndEncrypt() {
        try {
            databaseTest.save();
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("data\\testReader.txt", databaseTest.reader.path);
    }

    @Test
    public void testReadEntries() {
        try {
            databaseTest.writer.writeEntry("test1", "testusername", "testpassword");
            databaseTest.writer.writeEntry("test2", "testusername2", "testpassword2");
            assertEquals(0, databaseTest.entries.size());
            ArrayList<Entry> readEntries = databaseTest.reader.readEntries();
            assertEquals(2, readEntries.size());
            databaseTest.entries.addAll(readEntries);
            assertEquals(2, databaseTest.entries.size());
            assertEquals("testusername", databaseTest.getEntryUserName("test1"));
            assertEquals("testpassword", databaseTest.getEntryPassword("test1"));
            assertEquals("testusername2", databaseTest.getEntryUserName("test2"));
            assertEquals("testpassword2", databaseTest.getEntryPassword("test2"));
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testRemoveEntry() {
        try {
            databaseTest.writer.writeEntry("test1", "testusername", "testpassword");
            databaseTest.writer.writeEntry("test2", "testusername2", "testpassword2");
            assertEquals(0, databaseTest.entries.size());
            ArrayList<Entry> readEntries = databaseTest.reader.readEntries();
            assertEquals(2, readEntries.size());
            databaseTest.reader.removeEntry("test1");
            readEntries = databaseTest.reader.readEntries();
            assertEquals(1, readEntries.size());
            databaseTest.reader.removeEntry("test2");
            readEntries = databaseTest.reader.readEntries();
            assertEquals(0, readEntries.size());
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }
}
