package ui;

//Represents a listener that allows communication between CreationDialog class and MainFrame class
public interface CreationListener {
    Boolean createNewAndVerify(String name, char[] password);
}
