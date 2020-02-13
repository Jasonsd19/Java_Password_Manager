package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    Database databaseTest;

    //The writeEntry() method is tested indirectly in the DatabaseTest.

    @BeforeEach
    public void setup() throws IOException {
        databaseTest = new Database("testWriter", "password");
    }

    @AfterEach
    public void saveAndEncrypt() {
        databaseTest.save();
    }

    @Test
    public void testConstructor() {
        assertEquals("data\\testWriter.txt", databaseTest.writer.path);
    }

    @Test
    public void testWriteEntry() throws IOException {
        databaseTest.loadEntries();
        assertEquals(0, databaseTest.entries.size());
        databaseTest.writer.writeEntry("test1", "testusername", "testpassword");
        databaseTest.writer.writeEntry("test2", "testusername2", "testpassword2");
        assertEquals(0, databaseTest.entries.size());
        databaseTest.loadEntries();
        assertEquals(2, databaseTest.entries.size());
    }
}
