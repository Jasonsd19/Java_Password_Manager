package model;

// Represents an entry in the password manager.
public class Entry {
    String entryName;
    String userName;
    private String password;
    private Cipher cipher;

    //EFFECTS: Creates an entry with entry name, username, and encrypted password.
    public Entry(String name, String userName, String password, Cipher cipher) {
        this.cipher = cipher;
        this.entryName = name;
        this.userName = userName;
        this.password = this.cipher.encryptPassword(password);
    }

    //EFFECTS: Decrypts and returns password
    public String getPassword() {
        return cipher.decryptPassword(this.password);
    }

    //EFFECTS: Returns entry formatted to Database text file standards.
    public String formattedEntry() {
        String password = getPassword();
        return entryName + "," + userName + "," + password + ",";
    }
}
