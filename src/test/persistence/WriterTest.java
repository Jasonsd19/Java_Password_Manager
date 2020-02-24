package persistence;

import model.Database;
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

public class WriterTest {
    Database databaseTest;

    //The writeEntry() method is tested indirectly in the DatabaseTest.

    @BeforeEach
    public void setup() {
        try {
            if (Files.exists(Paths.get("data\\testWriter.txt"))) {
                databaseTest = new Database("data\\testWriter.txt", "testWriter", "password");
                databaseTest.entries = new ArrayList<>();
                File file = new File("data\\" + databaseTest.databaseName + ".txt");
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.println("Entry Name, Username, Password,");
                writer.close();
            } else {
                databaseTest = new Database("testWriter", "password");
            }
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @AfterEach
    public void saveAndEncrypt() {
        try {
            databaseTest.save();
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("This shouldn't print out.");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("data\\testWriter.txt", databaseTest.writer.path);
    }

    @Test
    public void testWriteEntry() {
        try {
            databaseTest.loadEntries();
            assertEquals(0, databaseTest.entries.size());
            databaseTest.writer.writeEntry("test1", "testusername", "testpassword");
            databaseTest.writer.writeEntry("test2", "testusername2", "testpassword2");
            assertEquals(0, databaseTest.entries.size());
            databaseTest.loadEntries();
            assertEquals(2, databaseTest.entries.size());
        } catch (IOException e) {
            fail();
            // shouldn't happen
        }
    }
}
