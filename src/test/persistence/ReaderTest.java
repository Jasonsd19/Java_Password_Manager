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

public class ReaderTest {
    Database databaseTest;

    // Both readEntries() and readEncryptedFile() are tested indirectly in DatabaseTest class.
    // Additionally readEncryptedFile() is tested whenever the database.load() method is called
    //              in the setup() and testReadEntries() methods.

    @BeforeEach
    public void setup() {
        try {
            Files.deleteIfExists(Paths.get("data\\testReader.json"));
            databaseTest = new Database("testReader", "password");
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void deleteDatabase() {
        try {
            Files.deleteIfExists(Paths.get("data\\testReader.json"));
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testReadEntries() {
        try {
            databaseTest.addNewEntry("test1", "testusername", "testpassword");
            databaseTest.addNewEntry("test2", "testusername2", "testpassword2");
            assertEquals(2, databaseTest.entries.size());
            databaseTest.save();
            databaseTest.entries = new ArrayList<>();
            assertEquals(0, databaseTest.entries.size());
            // load() method calls readEntries()
            databaseTest.load();
            assertEquals(2, databaseTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }
}