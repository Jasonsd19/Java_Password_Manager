package model;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

//Represents a cipher that can encrypt and decrypt passwords or text files.
public class Cipher {
    private String encryptedPassword;

    //EFFECTS: Constructs a cipher that encrypts given password and uses it as a key.
    public Cipher(String password) {
        StandardStringDigester encryptor = new StandardStringDigester();
        encryptor.setSaltSizeBytes(0);
        encryptedPassword = encryptor.digest(password);
    }

    //EFFECTS: Encrypts text file with encryptedPassword as key.
    //IOException an exception raised if file is not found or error in reading/writing file
    public String encryptText(String path) throws IOException {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        return basicTextEncryptor.encrypt(text);
    }

    //EFFECTS: Decrypts text file using encryptedPassword as key.
    //IOException an exception raised if file is not found or error in reading/writing file
    //EncryptionOperationNotPossibleException an exception raised if encryptedPassword is
    //                                        not the same password used to encrypt text
    public String decryptText(String path) throws IOException, EncryptionOperationNotPossibleException {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String text = bufferedReader.readLine();
        return basicTextEncryptor.decrypt(text);
    }

    //EFFECTS: Encrypts given password using encryptedPassword as key.
    public String encryptPassword(String password) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        return basicTextEncryptor.encrypt(password);
    }

    //EFFECTS: Decrypts given password using encryptedPassword as key.
    public String decryptPassword(String passToDecrypt) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(encryptedPassword);
        return basicTextEncryptor.decrypt(passToDecrypt);
    }
}
