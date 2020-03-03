package ui;

//Represents a listener that allows communication between ToolBar class and MainFrame class
public interface ToolBarListener {

    void buttonListener(String buttonOperation);

    Boolean isRowSelected();
}
