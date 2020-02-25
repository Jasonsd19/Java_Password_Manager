package model;

// Represents an entry in the password manager.
public class Entry {
    public String entryName;
    public String userName;
    private String password;
    private Cipher cipher;

    //EFFECTS: Creates an entry with entry name, username, and encrypted password.
    public Entry(String name, String userName, String password, Cipher cipher) {
        this.cipher = cipher;
        this.entryName = name;
        this.userName = userName;
        this.password = this.cipher.encryptTextOrPassword(password);
    }

    //EFFECTS: Decrypts and returns password
    public String getPassword() {
        return cipher.decryptTextOrPassword(this.password);
    }
}