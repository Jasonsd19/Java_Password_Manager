package model;

// Represents an entry in the password manager.
public class Entry {
    public String entryName;
    public String userName;
    public String password;
    public Cipher cipher;

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

    //EFFECTS: Sets entryName to given newName
    public void setEntryName(String newName) {
        this.entryName = newName;
    }

    //EFFECTS: Sets entry username to given newUserName
    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    //EFFECTS: Sets entry password to given newPassword
    public void setPassword(String newPassword) {
        this.password = this.cipher.encryptTextOrPassword(newPassword);
    }
}