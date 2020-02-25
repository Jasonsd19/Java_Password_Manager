package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {
    Cipher cipherTest;
    String password = "testPassword";

    @BeforeEach
    public void setup() {
        cipherTest = new Cipher(password);
    }

    @Test
    public void testEncryptTextOrPassword() {
        String plainText = "This is plain text.";
        String encryptedText = cipherTest.encryptTextOrPassword(plainText);
        assertFalse(plainText.equals(encryptedText));
    }

    @Test
    public void testDecryptTextOrPassword() {
        String plainText = "This is also plain text.";
        String encryptedText = cipherTest.encryptTextOrPassword(plainText);
        assertFalse(plainText.equals(encryptedText));
        String decryptedText = cipherTest.decryptTextOrPassword(encryptedText);
        assertTrue(plainText.equals(decryptedText));
    }
}