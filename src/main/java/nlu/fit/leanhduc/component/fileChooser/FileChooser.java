package nlu.fit.leanhduc.component.fileChooser;

import nlu.fit.leanhduc.util.Constraint;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class FileChooser extends JPanel {
    private JFileChooser fileChooser;
    private JLabel label;
    private JButton button;
    private FileChooserEvent event;

    public FileChooser() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout(5, 5));


        createButton();
        createLabel();
        this.add(label, BorderLayout.CENTER);
        this.add(button, BorderLayout.EAST);
        Border combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        this.setBorder(combinedBorder);
    }

    private void createLabel() {
        label = new JLabel();
        label.setPreferredSize(new Dimension(100, 30));
        label.setFont(Constraint.FONT_MEDIUM);
    }

    private void createButton() {
        button = new JButton("Open");
        button.addActionListener(e -> click());

    }

    private void click() {
        fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        switch (option) {
            case JFileChooser.APPROVE_OPTION:
                File selectedFile = fileChooser.getSelectedFile();
                String name = selectedFile.getName();
                label.setText(name);
                this.event.onFileSelected(selectedFile);
                break;
            case JFileChooser.CANCEL_OPTION:
                this.event.onFileUnselected();
                break;
            case JFileChooser.ERROR_OPTION:
                this.event.onError("Error");
                break;
        }
    }

    public void cancel() {
        label.setText("");
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public FileChooserEvent getEvent() {
        return event;
    }

    public void setEvent(FileChooserEvent event) {
        this.event = event;
    }
}
