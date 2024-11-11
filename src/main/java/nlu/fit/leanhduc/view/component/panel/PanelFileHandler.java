package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelFileHandler extends PanelHandler {
    JPanel fileEncrypt, fileDecrypt;

    public PanelFileHandler() {
    }

    @Override
    protected void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel panel1 = new JPanel();
        this.add(panel1);
        this.fileEncrypt = new FileChooser();
        panel1.add(this.fileEncrypt);

        Border combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Mã hóa file"));
        panel1.setBorder(combinedBorder);

        JPanel panel2 = new JPanel();
        this.add(panel2);
        this.fileDecrypt = new FileChooser();
        panel2.add(this.fileDecrypt);

        combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Giải mã file"));
        panel2.setBorder(combinedBorder);
    }
}
