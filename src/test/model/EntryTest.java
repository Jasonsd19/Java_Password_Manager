package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    Cipher cipher;
    String password = "password";
    Entry testEntry;
    Entry testEntryTwo;

    @Before
    public void setup() {
        cipher = new Cipher(password);
        testEntry = new Entry("test1", "testusername", "testpassword", cipher);
        testEntryTwo = new Entry("test2", "testusername2", "testpassword2", cipher);
    }

    @Test
    public void testConstructor() {
        assertEquals("test1", testEntry.entryName);
        assertEquals("test2", testEntryTwo.entryName);
        assertEquals("testusername", testEntry.userName);
        assertEquals("testusername2", testEntryTwo.userName);
    }

    @Test
    public void testGetPassword() {
        assertEquals("testpassword", testEntry.getPassword());
        assertEquals("testpassword2", testEntryTwo.getPassword());
    }

    @Test
    public void testFormattedEntry() {
        assertEquals("test1,testusername,testpassword,", testEntry.formattedEntry());
        assertEquals("test2,testusername2,testpassword2,", testEntryTwo.formattedEntry());
    }
}
