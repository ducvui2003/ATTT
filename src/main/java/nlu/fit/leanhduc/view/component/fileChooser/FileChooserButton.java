package nlu.fit.leanhduc.view.component.fileChooser;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@Getter
@Setter
public class FileChooserButton extends JButton {
    String text;
    ImageIcon icon;
    Component container;
    String path;
    FileChooserEvent event;

    public FileChooserButton(String text, ImageIcon icon) {
        this.text = text;
        this.icon = icon;
        init();
    }

    private void init() {
        this.setText(this.text);
        this.setIcon(this.icon);
        this.setPreferredSize(new Dimension(200, 40));
        this.setBackground(Color.WHITE);
        this.setEvent();
    }

    private void setEvent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(container);

                switch (option) {
                    case JFileChooser.APPROVE_OPTION:
                        File selectedFile = fileChooser.getSelectedFile();
                        event.autoAddExtension(selectedFile);
                        event.onFileSelected(selectedFile);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        event.onFileUnselected();
                        break;
                    case JFileChooser.ERROR_OPTION:
                        event.onError("Error");
                        break;
                }
            }
        });
    }
}
