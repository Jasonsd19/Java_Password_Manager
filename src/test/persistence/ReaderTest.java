package persistence;

import model.Database;
import model.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    Database databaseTest;

    // Both readEntries() and readEncryptedFile() are tested indirectly in DatabaseTest class.
    // Additionally readEncryptedFile() is tested whenever the database.load() method is called
    //              in the setup() and testReadEntries() methods.

    @BeforeEach
    public void setup(){
        try {
            databaseTest = new Database("data\\testReader.json", "testReader", "password");
            databaseTest.entries = new ArrayList<>();
            databaseTest.save();
            databaseTest = new Database("data\\testReader.json", "testReader", "password");
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
        assertEquals("data\\testReader.json", databaseTest.reader.path);
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
            databaseTest = new Database("data\\testReader.json", "testReader", "password");
            assertEquals(2, databaseTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }
}