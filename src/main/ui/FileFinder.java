package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//Represents a JPanel that incorporates file finder functionality
public class FileFinder extends JPanel implements ActionListener {

    public JLabel filePathLabel;
    public JTextField filePath;
    public JButton findFileButton;
    private JFileChooser fileChooser;

    //EFFECTS: Sets up layout for FileFinder JPanel
    public FileFinder() {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(5);
        setLayout(layout);

        filePathLabel = new JLabel("Choose File: ");
        filePath = new JTextField(20);
        findFileButton = new JButton("Find  File");
        fileChooser = new JFileChooser("data");

        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("json files (*.json)",
                "json");

        fileChooser.addChoosableFileFilter(fileNameExtensionFilter);
        fileChooser.setFileFilter(fileNameExtensionFilter);

        findFileButton.addActionListener(this);

        add(filePathLabel, BorderLayout.WEST);
        add(filePath, BorderLayout.CENTER);
        add(findFileButton, BorderLayout.EAST);

    }

    @Override
    //MODIFIES: this
    //EFFECTS: If user clicks find file button then displays path of user selected file in filePath JTextField.
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(findFileButton)) {
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.getPath().contains(".json")) {
                    filePath.setText(file.getPath());
                }
            }
        }
    }
}
