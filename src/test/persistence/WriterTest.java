package persistence;

import model.Database;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    Database databaseTest;

    // Both writeEntriesToFile() and writeDecryptedTextToFile() are tested indirectly in DatabaseTest class.
    // Additionally writeDecryptedTextToFile() is tested whenever the database.load() method is called in the
    //              setup() and testWriteEntriesToFile() methods.

    @BeforeEach
    public void setup() {
        try {
            Files.deleteIfExists(Paths.get("data\\testWriter.json"));
            databaseTest = new Database("testWriter", "password");
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void deleteDatabase() {
        try {
            Files.deleteIfExists(Paths.get("data\\testWriter.json"));
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
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
            databaseTest.load();
            assertEquals(2, databaseTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }
}
