package ui;

public interface LoginListener {

    Boolean loadDatabaseAndVerify(String path, char[] password);

    void dialogSwitcher();
}
