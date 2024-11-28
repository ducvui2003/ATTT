package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelFileVerify extends JPanel implements FileChooserEvent {
    FileChooser fileChooserData;
    JPanel container;
    JButton btnVerify;
    JTextArea signatureTextBlock;
    String src;
    PanelFileVerifyEvent event;

    public PanelFileVerify(PanelFileVerifyEvent event) {
        this.event = event;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);
        this.fileChooserData = new FileChooser();
        this.fileChooserData.setEvent(this);
        this.btnVerify = new JButton("Xác thực", null);
        this.signatureTextBlock = SwingComponentUtil.createTextArea();
        this.signatureTextBlock.setEnabled(true);
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)         
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)  
                        .build(),
                new JLabel("File cần xác thực"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1.5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                fileChooserData);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)         
                        .weight(0.15, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)  
                        .build(),
                new JLabel("Chữ  ký"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)
                        .weight(1.5, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                new JScrollPane(signatureTextBlock));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 3)
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
                        .build(),
                btnVerify);
        handleVerify();
    }

    private void handleVerify(){
        btnVerify.addActionListener(e -> {
            if (src == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn file cần xác thực", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            event.onVerifyFile(src, signatureTextBlock.getText());
        });
    }

    @Override
    public void onFileSelected(File file) {
        this.src = file.getAbsolutePath();
    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }
}
