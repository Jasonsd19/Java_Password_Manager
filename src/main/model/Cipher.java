package model;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

//Represents a cipher that can encrypt and decrypt passwords or text files.
public class Cipher {
    public String encryptedPassword;

    //EFFECTS: Constructs a cipher that encrypts given password and uses it as a key.
    public Cipher(String password) {
        StandardStringDigester encryptor = new StandardStringDigester();
        encryptor.setSaltSizeBytes(0);
        encryptedPassword = encryptor.digest(password);
    }

    //EFFECTS: Encrypts give text using encryptedPassword as key.
    public String encryptTextOrPassword(String text) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        return basicTextEncryptor.encrypt(text);
    }

    //REQUIRES: Given text has been encrypted using the same key.
    //EFFECTS: Decrypts given text using encryptedPassword as key.
    public String decryptTextOrPassword(String text) throws EncryptionOperationNotPossibleException {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        return basicTextEncryptor.decrypt(text);
    }
}