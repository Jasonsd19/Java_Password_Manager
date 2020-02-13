package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {
    Cipher cipherTest;
    String password = "testpassword";

    // Only testing encryptPassword() and decryptPassword() because they work exactly the same as
    // encryptText() and decryptText() the only difference is that the former methods work on strings
    // and the latter methods work on text files. The actually text file tests would be difficult to
    // implement and the actual handling of the text files is covered by DatabaseTest, ReaderTest, and
    // WriterTest.

    @Before
    public void setup() {
        cipherTest = new Cipher(password);
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
