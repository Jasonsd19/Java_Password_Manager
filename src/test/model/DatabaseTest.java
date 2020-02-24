package model;

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

public class DatabaseTest {
    Database databaseNewTest;
    Database databaseLoadTest;

    // methods save() and load() are integrated with constructors and many other methods
    // if all other methods pass then save() and load() are working as expected.

    @BeforeEach
    public void setup(){
        try {
            if (Files.exists(Paths.get("data\\testNewFile.txt"))) {
                databaseNewTest = new Database("data\\testNewFile.txt", "testNewFile", "password");
                databaseNewTest.entries = new ArrayList<>();
                File file = new File("data\\" + databaseNewTest.databaseName + ".txt");
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.println("Entry Name, Username, Password,");
                writer.close();
            } else {
                databaseNewTest = new Database("testNewFile", "password");
            }
            databaseLoadTest = new Database("data\\testFile.txt", "testFile", "password");
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void saveAndEncrypt() {
        try {
            databaseNewTest.save();
            databaseLoadTest.save();
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testNewConstructor() {
        try {
            Database testDatabase = new Database("newNewTest", "password");
            assertEquals("newNewTest", testDatabase.databaseName);
            assertEquals(0, testDatabase.entries.size());
            Files.deleteIfExists(Paths.get("data\\newNewTest.txt"));
        } catch (IOException e) {
            // shouldn't happen
            fail();
        }
    }

    @Test
    public void testNewDuplicateDatabase() {
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
        try {
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
            assertEquals(2, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
            assertEquals(2, databaseNewTest.entries.size());
            assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testRemoveEntry() {
        try {
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test2", "testusername2", "testpassword2");
            assertEquals(2, databaseNewTest.entries.size());
            assertEquals("testFile", databaseLoadTest.databaseName);
            databaseNewTest.removeEntry("nothing");
            assertEquals(2, databaseNewTest.entries.size());
            databaseNewTest.removeEntry("test");
            assertEquals(1, databaseNewTest.entries.size());
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testIsUnique() {
        try {
            assertTrue(databaseNewTest.isUnique("test"));
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertFalse(databaseNewTest.isUnique("test"));
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testLoadEntries() {
        try {
            assertEquals(0, databaseNewTest.entries.size());
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals(1, databaseNewTest.entries.size());
            databaseNewTest.save();
            // load constructor method also calls loadEntries() method.
            databaseNewTest = new Database("data\\testNewFile.txt", "testNewFile", "password");
            assertEquals(1, databaseNewTest.entries.size());
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testGetEntryUsername(){
        try {
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals("testusername", databaseNewTest.getEntryUserName("test"));
            assertNotEquals("testusername", databaseNewTest.getEntryUserName("bleh"));
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }

    @Test
    public void testGetEntryPassword(){
        try {
            databaseNewTest.addNewEntry("test", "testusername", "testpassword");
            assertEquals("testpassword", databaseNewTest.getEntryPassword("test"));
            assertNotEquals("testpassword", databaseNewTest.getEntryPassword("bleh"));
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }
}
