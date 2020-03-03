package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileFinder extends JPanel implements ActionListener {

    public JLabel filePathLabel;
    public JTextField filePath;
    public JButton findFileButton;
    private JFileChooser fileChooser;

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
