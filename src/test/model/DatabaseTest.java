package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    Database databaseLoadTest;

    // methods save() and load() are integrated with constructors and many other methods
    // if all other methods pass then save() and load() are working as expected.

    @BeforeEach
    public void setup(){
        try {
            databaseLoadTest = new Database("data\\testFile.json", "testFile", "password");
            databaseLoadTest.entries = new ArrayList<>();
            databaseLoadTest.save();
            databaseLoadTest.load();
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void saveAndEncrypt() {
        try {
            databaseLoadTest.save();
        } catch (IOException e) {
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testNewConstructor() {
        try {
            Database testDatabase = new Database("newNewTest", "password");
            assertEquals("newNewTest", testDatabase.databaseName);
            assertEquals(0, testDatabase.entries.size());
            Files.deleteIfExists(Paths.get("data\\newNewTest.json"));
        } catch (IOException e) {
            // shouldn't happen
            fail();
        }
    }

    @Test
    public void testNewConstructorFileAlreadyExistsException() {
        try {
            new Database("testNewFile", "password");
            fail();
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testLoadConstructor() {
        assertEquals("testFile", databaseLoadTest.databaseName);
        assertEquals(0, databaseLoadTest.entries.size());
    }

    @Test
    public void testAddNewEntry() {
        databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseLoadTest.entries.size());
        databaseLoadTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseLoadTest.entries.size());
        databaseLoadTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseLoadTest.entries.size());
        assertEquals("testusername", databaseLoadTest.getEntryUserName("test"));
    }

    @Test
    public void testRemoveEntry() {
        databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseLoadTest.entries.size());
        databaseLoadTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseLoadTest.entries.size());
        assertFalse(databaseLoadTest.removeEntry("nothing"));
        assertEquals(2, databaseLoadTest.entries.size());
        assertTrue(databaseLoadTest.removeEntry("test"));
        assertEquals(1, databaseLoadTest.entries.size());
    }

    @Test
    public void testIsUnique() {
        assertTrue(databaseLoadTest.isUnique("test"));
        databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
        assertFalse(databaseLoadTest.isUnique("test"));
    }

    @Test
    public void testLoadEntries() {
        try {
            assertEquals(0, databaseLoadTest.entries.size());
            databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseLoadTest.entries.size());
            databaseLoadTest.save();
            // load constructor method also calls loadEntries() method.
            databaseLoadTest = new Database("data\\testFile.json", "testFile", "password");
            assertEquals(1, databaseLoadTest.entries.size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testGetEntryUsername(){
        databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testusername", databaseLoadTest.getEntryUserName("test"));
        assertNotEquals("testusername", databaseLoadTest.getEntryUserName("bleh"));
    }

    @Test
    public void testGetEntryPassword(){
        databaseLoadTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testpassword", databaseLoadTest.getEntryPassword("test"));
        assertNotEquals("testpassword", databaseLoadTest.getEntryPassword("bleh"));
    }
}
