package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    Database databaseNewTest;
    Database databaseLoadTest;

    // methods save() and load() are integrated with constructors and many other methods
    // if all other methods pass then save() and load() are working as expected.

    @BeforeEach
    public void setup() throws IOException {
        databaseNewTest = new Database("testNewFile", "password");
        databaseLoadTest = new Database("data\\testFile.txt", "testFile", "password");
    }

    @AfterEach
    public void saveAndEncrypt() {
        databaseNewTest.save();
        databaseLoadTest.save();
    }

    @Test
    public void testNewConstructor() {
        assertEquals("testNewFile", databaseNewTest.databaseName);
        assertEquals(0, databaseNewTest.entries.size());
    }

    @Test
    public void testLoadConstructor() {
        assertEquals("testFile", databaseLoadTest.databaseName);
        assertEquals(0, databaseLoadTest.entries.size());
    }

    @Test
    public void testAddNewEntry() throws IOException {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
    }

    @Test
    public void testRemoveEntry() throws IOException {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseNewTest.entries.size());
        assertEquals("testFile", databaseLoadTest.databaseName);
        databaseNewTest.removeEntry("nothing");
        assertEquals(2, databaseNewTest.entries.size());
        databaseNewTest.removeEntry("test");
        assertEquals(1, databaseNewTest.entries.size());
    }

    @Test
    public void testIsUnique() throws IOException {
        assertTrue(databaseNewTest.isUnique("test"));
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertFalse(databaseNewTest.isUnique("test"));
    }

    @Test
    public void testLoadEntries() throws IOException {
        assertEquals(0, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.save();
        // load constructor method also calls loadEntries() method.
        databaseNewTest = new Database("data\\testNewFile.txt", "testNewFile", "password");
        assertEquals(1, databaseNewTest.entries.size());
    }

    @Test
    public void testGetEntryUsername() throws IOException {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
        assertNotEquals("testusername", databaseNewTest.getEntryUserName("bleh"));
    }

    @Test
    public void testGetEntryPassword() throws IOException {
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testpassword", databaseNewTest.getEntryPassword("test"));
        assertNotEquals("testpassword", databaseNewTest.getEntryPassword("bleh"));
    }
}
