package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelFileHandler extends PanelHandler implements FileChooserEvent {
    JPanel container;
    final String commandEncrypt = "encrypt";
    final String commandDecrypt = "decrypt";
    FileChooser fileChooserOriginal, fileChooserEncrypt;
    FileChooserButton btnEncrypt, btnDecrypt;
    protected PanelFileHandlerEvent event;

    public PanelFileHandler(PanelFileHandlerEvent event) {
        this.event = event;
    }

    @Override
    protected void init() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);

        this.fileChooserOriginal = new FileChooser();
        this.fileChooserOriginal.setEvent(this);
        this.fileChooserEncrypt = new FileChooser();
        this.fileChooserEncrypt.setEvent(this);
        int leftMargin = 10;

        this.btnEncrypt = new FileChooserButton("Mã hóa", null);
        this.btnEncrypt.setActionCommand(commandEncrypt);

        this.btnDecrypt = new FileChooserButton("Giải mã", null);
        this.btnDecrypt.setActionCommand(commandDecrypt);


        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)         
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0)  
                        .build(),
                new JLabel("File cần mã hóa"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)         
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
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
                        .grid(0, 3)         
                        .gridSpan(6, 1)     
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
                        .build(),
                SwingComponentUtil.createSeparator());

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0)  
                        .build(),
                new JLabel("File cần giải mã"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
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

        setEvent();
    }

    @Override
    public void onFileSelected(File file) {

    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }

    public void setEvent() {
        this.btnEncrypt.setEvent(new FileChooserEvent() {
            @Override
            public void onFileSelected(File file) {
                String src = fileChooserOriginal.getPath();
                if (src == null) {
                    JOptionPane.showMessageDialog(null, "Chưa chọn file cần mã hóa", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String dest = file.getAbsolutePath();
                event.onEncryptFile(src, dest);
            }

            @Override
            public void onFileUnselected() {

            }

            @Override
            public void onError(String message) {

            }
        });

        this.btnDecrypt.setEvent(new FileChooserEvent() {
            @Override
            public void onFileSelected(File file) {
                String src = fileChooserEncrypt.getPath();
                String dest = file.getAbsolutePath();
                event.onDecryptFile(src, dest);
            }

            @Override
            public void onFileUnselected() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

}
