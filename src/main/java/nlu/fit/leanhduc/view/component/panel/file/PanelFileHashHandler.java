package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PanelFileHashHandler extends PanelHandler implements ActionListener {
    final String commandHash = "hash";
    FileChooserButton plainTextBlock;
    JTextField hashBlock;
    JButton btnHash;
    PanelFileHashEvent event;
    String filePath;

    public PanelFileHashHandler(PanelFileHashEvent event) {
        this.event = event;
    }

    @Override
    protected void init() {
        this.setLayout(new GridBagLayout());

        this.plainTextBlock = new FileChooserButton("Chọn file", MetadataConfig.getINSTANCE().getUploadIcon());
        this.hashBlock = new JTextField(20);
        int leftMargin = 10;
        this.btnHash = new JButton("Hash");
        this.btnHash.setActionCommand(commandHash);
        this.btnHash.addActionListener(this);


        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("Chọn file"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)        // Starting at the first column in the desired row
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                plainTextBlock);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản đã hash"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)        // Starting at the first column in the desired row
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                hashBlock);
        JPanel panelContainBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelContainBtn.add(btnHash);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)        // Starting at the first column in the desired row
                        .weight(0.5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                panelContainBtn);

        handleEvent();
    }

    private void handleEvent() {
        this.plainTextBlock.setEvent(new FileChooserEvent() {
            @Override
            public void onFileSelected(File file) {
                filePath = file.getAbsolutePath();
            }

            @Override
            public void onFileUnselected() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(commandHash)) {
            if (filePath == null) {
                JOptionPane.showMessageDialog(this.getParent(), "Chưa chọn file", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            hashBlock.setText(event.onHashFile(filePath));
        }
    }

}
