package view.section;

import component.fileChooser.FileChooser;
import component.fileChooser.FileChooserEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class InputSection extends JPanel implements FileChooserEvent {
    FileChooser fileChooser;

    public InputSection() {
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(5));
        fileChooser = new FileChooser();
        fileChooser.setEvent(this);
        this.add(fileChooser);
        Border border = BorderFactory.createTitledBorder("Input");
        this.setBorder(border);
    }

    @Override
    public void onFileSelected(File file) {

    }

    @Override
    public void onFileUnselected() {
        fileChooser.cancel();
    }

    @Override
    public void onError(String message) {

    }

}
