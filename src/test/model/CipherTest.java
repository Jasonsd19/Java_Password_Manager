package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {
    Cipher cipherTest;
    String password = "testpassword";
    Database databaseTest;

    // Testing encryptPassword() and decryptPassword() because they work exactly the same as
    // encryptText() and decryptText() the only difference is that the former methods work on strings
    // and the latter methods work on text files. The actually text file tests would be difficult to
    // implement and the actual handling of the text files is covered by DatabaseTest, ReaderTest, and
    // WriterTest.

    @Before
    public void setup() {
        cipherTest = new Cipher(password);
        databaseTest = new Database("cipherTest", "password");
    }

    @After
    public void saveAdEncrypt() {
        databaseTest.save();
    }

    @Test
    public void testEncryptText() {
        try {
            String text = new String(Files.readAllBytes(Paths.get(databaseTest.path)), StandardCharsets.UTF_8);
            databaseTest.save();
            String encrypt = new String(Files.readAllBytes(Paths.get(databaseTest.path)), StandardCharsets.UTF_8);
            assertFalse(text.equals(encrypt));
            databaseTest.load();
        } catch (IOException e) {
            // shouldn't happen
        }
    }

    @Test
    public void testDecryptText() {
        try {
            String text = new String(Files.readAllBytes(Paths.get(databaseTest.path)), StandardCharsets.UTF_8);
            databaseTest.save();
            String encrypt = new String(Files.readAllBytes(Paths.get(databaseTest.path)), StandardCharsets.UTF_8);
            assertFalse(text.equals(encrypt));
            databaseTest.load();
            String decrypt = new String(Files.readAllBytes(Paths.get(databaseTest.path)), StandardCharsets.UTF_8);
            assertTrue(text.equals(decrypt));
        } catch (IOException e) {
            // shouldn't happen
        }
    }

    @Test
    public void testEncryptPassword() {
        String plainText = "This is plain text.";
        String encryptedText = cipherTest.encryptPassword(plainText);
        assertFalse(plainText.equals(encryptedText));
    }

    @Test
    public void testDecryptPassword() {
        String plainText = "This is also plain text.";
        String encryptedText = cipherTest.encryptPassword(plainText);
        assertFalse(plainText.equals(encryptedText));
        String decryptedText = cipherTest.decryptPassword(encryptedText);
        assertTrue(plainText.equals(decryptedText));
    }
}

