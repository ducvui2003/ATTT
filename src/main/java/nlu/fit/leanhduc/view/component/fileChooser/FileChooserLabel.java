package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.view.component.FileHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileChooserLabel extends JLabel {
    String text;
    ImageIcon icon;
    FileHelper fileHelper;
    Component container;

    public FileChooserLabel(String text, ImageIcon icon) {
        this.text = text;
        this.icon = icon;
    }

    private void init() {
        this.setText(this.text);
        this.setIcon(this.icon);
        this.setPreferredSize(new Dimension(200, 40));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileHelper.showOpenFile(container, null, new String[]{"txt", "doc", "docx", "pdf"});
            }
        });
    }
}
