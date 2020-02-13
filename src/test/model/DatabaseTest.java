package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    Database databaseNewTest;
    Database databaseLoadTest;

    // methods save() and load() are integrated with constructors and many other methods
    // if all other methods pass then save() and load() are working as expected.

    @Before
    public void setup(){
        databaseNewTest = new Database("testNewFile", "password");
        databaseLoadTest = new Database("data\\testFile.txt", "testFile", "password");
    }

    @After
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
        assertEquals("testFile", databaseLoadTest.databaseName);
        databaseNewTest.removeEntry("nothing");
        assertEquals(2, databaseNewTest.entries.size());
        databaseNewTest.removeEntry("test");
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
        assertEquals(0, databaseNewTest.entries.size());
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals(1, databaseNewTest.entries.size());
        databaseNewTest.save();
        // load constructor method also calls loadEntries() method.
        databaseNewTest = new Database("data\\testNewFile.txt", "testNewFile", "password");
        assertEquals(1, databaseNewTest.entries.size());
    }

    @Test
    public void testGetEntryUsername(){
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
    }

    @Test
    public void testGetEntryPassword(){
        databaseNewTest.addNewEntry("test", "testusername", "testpassword");
        assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
    }
}
