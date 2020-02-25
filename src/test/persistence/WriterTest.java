package persistence;

import model.Database;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    Database databaseTest;

    // Both writeEntriesToFile() and writeDecryptedTextToFile() are tested indirectly in DatabaseTest class.
    // Additionally writeDecryptedTextToFile() is tested whenever the database.load() method is called in the
    //              setup() and testWriteEntriesToFile() methods.

    @BeforeEach
    public void setup(){
        try {
            databaseTest = new Database("data\\testWriter.json", "testWriter", "password");
            databaseTest.entries = new ArrayList<>();
            databaseTest.save();
            databaseTest = new Database("data\\testWriter.json", "testWriter", "password");
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void saveAndEncrypt() {
        try {
            databaseTest.save();
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("data\\testWriter.json", databaseTest.writer.path);
    }

    @Test
    public void testWriteEntriesToFile() {
        try {
            databaseTest.addNewEntry("test1", "testusername", "testpassword");
            databaseTest.addNewEntry("test2", "testusername2", "testpassword2");
            assertEquals(2, databaseTest.entries.size());
            // save() method calls writeEntriesToFile()
            databaseTest.save();
            databaseTest.entries = new ArrayList<>();
            assertEquals(0, databaseTest.entries.size());
            // load() method adds previous two entries to newly instantiated entries ArrayList from json file
            databaseTest = new Database("data\\testWriter.json", "testWriter", "password");
            assertEquals(2, databaseTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }
}
