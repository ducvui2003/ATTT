package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserLabel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelFileHandlerEncrypt extends JPanel {
    FileChooserLabel labelFileEncryptInput, labelFileEncryptOutput;
    JButton btnEncrypt;

    public PanelFileHandlerEncrypt() {
        this.init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.labelFileEncryptInput = new FileChooserLabel("Chọn file", MetadataConfig.getINSTANCE().getUploadIcon());
        this.labelFileEncryptOutput = new FileChooserLabel("Chọn file", MetadataConfig.getINSTANCE().getUploadIcon());

        this.btnEncrypt = new JButton("Mã hóa");
        this.btnEncrypt.setIcon(MetadataConfig.getINSTANCE().getEncryptIcon());

        JPanel panel = new JPanel(new GridBagLayout());
        Border combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Mã hóa file"));
        panel.setBorder(combinedBorder);
        this.add(panel, BorderLayout.CENTER);

        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder().grid(0, 0).fill(GridBagConstraints.HORIZONTAL)
                        .insets(0, 10, 10, 0).build(),
                new JLabel("File cần mã hóa")
        );

        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder().grid(1, 0).fill(GridBagConstraints.HORIZONTAL)
                        .weight(3.0, 1.0)
                        .insets(0, 10, 10, 0).build(),
                labelFileEncryptInput
        );


        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder().grid(0, 1).fill(GridBagConstraints.HORIZONTAL)
                        .insets(0, 10, 10, 0).build(),
                new JLabel("Lưu file tại")
        );

        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder().grid(1, 1).fill(GridBagConstraints.HORIZONTAL)
                        .weight(3.0, 1.0)
                        .insets(0, 10, 10, 0).build(),
                labelFileEncryptOutput
        );

        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder().grid(0, 2).fill(GridBagConstraints.HORIZONTAL)
                        .gridSpan(2, 0)
                        .insets(10, 10, 10, 10).build(),
                btnEncrypt
        );
    }
}
