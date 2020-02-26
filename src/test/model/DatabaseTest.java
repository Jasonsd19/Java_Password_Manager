package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    Database databaseNewTest;

    // methods save() and load() are integrated with constructors and many other methods
    // if all other methods pass then save() and load() are working as expected.

    @BeforeEach
    public void setup() {
        try {
            Files.deleteIfExists(Paths.get("data\\newTestFile.json"));
            databaseNewTest = new Database("newTestFile", "password");
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void deleteDatabase() {
        try {
            Files.deleteIfExists(Paths.get("data\\newTestFile.json"));
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testFileAlreadyExistsException() {
        try {
            assertEquals(0, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseNewTest.entries.size());
            databaseNewTest = new Database("newTestFile", "password");
            fail();
        } catch (IOException e) {
            assertEquals(1, databaseNewTest.entries.size());
        }
    }

    @Test
    public void testAddNewEntry() {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
    }

    @Test
    public void testRemoveEntry() {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        assertFalse(databaseNewTest.removeEntry("nothing"));
        assertEquals(2, databaseNewTest.entries.size());
        assertTrue(databaseNewTest.removeEntry("test"));
        assertEquals(1, databaseNewTest.entries.size());
    }

    @Test
    public void testIsUnique() {
        assertTrue(databaseNewTest.isUnique("test"));
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertFalse(databaseNewTest.isUnique("test"));
    }

    @Test
    public void testLoadEntries() {
        try {
            assertEquals(0, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseNewTest.entries.size());
            databaseNewTest.save();
            // load constructor method also calls loadEntries() method.
            databaseNewTest = new Database("data\\newTestFile.json", "newTestFile", "password");
            assertEquals(1, databaseNewTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testGetEntryUsername() {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
        assertNotEquals("testusername", databaseNewTest.getEntryUserName("bleh"));
    }

    @Test
    public void testGetEntryPassword() {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testpassword", databaseNewTest.getEntryPassword("test"));
        assertNotEquals("testpassword", databaseNewTest.getEntryPassword("bleh"));
    }
}
