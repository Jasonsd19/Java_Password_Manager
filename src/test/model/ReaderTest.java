package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    Database databaseTest;

    //Both readEntries() and removeEntries() are tested indirectly in DatabaseTest.

    @BeforeEach
    public void setup() throws IOException {
        databaseTest = new Database("testReader", "password");
    }

    @AfterEach
    public void saveAndEncrypt() {
        databaseTest.save();
    }

    @Test
    public void testConstructor() {
        assertEquals("data\\testReader.txt", databaseTest.reader.path);
    }

    @Test
    public void testReadEntries() throws IOException {
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
    }

    @Test
    public void testRemoveEntry() throws IOException {
        databaseTest.addNewEntry("test1", "testusername", "testpassword");
        databaseTest.addNewEntry("test2", "testusername2", "testpassword2");
        assertEquals(2, databaseTest.entries.size());
        databaseTest.removeEntry("test1");
        assertEquals(1, databaseTest.entries.size());
        databaseTest.removeEntry("test2");
        assertEquals(0, databaseTest.entries.size());
    }
}
