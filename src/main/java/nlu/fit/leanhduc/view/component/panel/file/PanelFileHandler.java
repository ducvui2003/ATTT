package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelFileHandler extends PanelHandler implements ActionListener {
    JPanel container;
    final String commandEncrypt = "encrypt";
    final String commandDecrypt = "decrypt";
    FileChooser fileChooserOriginal, fileChooserEncrypt;
    FileChooserButton btnEncrypt, btnDecrypt;
    protected PanelFileHandlerEvent event;

    public PanelFileHandler() {
    }

    @Override
    protected void init() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);

        this.fileChooserOriginal = new FileChooser();
        this.fileChooserEncrypt = new FileChooser();
        int leftMargin = 10;

        this.btnEncrypt = new FileChooserButton("Mã hóa", null);
        this.btnEncrypt.setActionCommand(commandEncrypt);
        this.btnEncrypt.addActionListener(this);

        this.btnDecrypt = new FileChooserButton("Giải mã", null);
        this.btnDecrypt.setActionCommand(commandDecrypt);
        this.btnDecrypt.addActionListener(this);


        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)        // Starting at the first column in the desired row
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("File cần mã hóa"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)        // Starting at the first column in the desired row
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                fileChooserOriginal);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
                        .build(),
                btnEncrypt);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 3)        // Starting at the first column in the desired row
                        .gridSpan(6, 1)    // Span 5 columns
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                SwingComponentUtil.createSeparator());

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản mã hóa"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                fileChooserEncrypt);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 5)
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
                        .build(),
                btnDecrypt);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
//        Object source = e.getSource();
//        if (source == btnEncrypt) {
//            String src = this.fileEncrypt.getPath();
//            String dest = this.saveFileEncrypt.getPath();
//            System.out.println("Encrypt: " + src + " -> " + dest);
//        }
    }
}
