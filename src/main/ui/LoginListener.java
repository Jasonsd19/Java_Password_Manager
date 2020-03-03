package ui;

//Represents a listener that allows communication between LoginDialog class and MainFrame class
public interface LoginListener {

    Boolean loadDatabaseAndVerify(String path, char[] password);

    void dialogSwitcher();
}
