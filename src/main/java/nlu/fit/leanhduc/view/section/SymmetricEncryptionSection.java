package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.view.component.dialog.GenerateKeyDialog;

import javax.swing.*;
import java.awt.*;

public class SymmetricEncryptionSection extends JPanel {
    Button btnLoadKey;
    Button btnGenerateKey;
    GenerateKeyDialog generateKeyDialog;

    public SymmetricEncryptionSection() {
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new BorderLayout(5, 5));
        createKeyPanel();
    }

    private void createKeyPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        this.add(panel, BorderLayout.NORTH);
        generateKeyDialog = new GenerateKeyDialog((Frame) this.getParent());
        btnLoadKey = new Button("Chọn khóa");
        btnGenerateKey = new Button("Tạo khóa mới");
        panel.add(btnGenerateKey);
        panel.add(btnLoadKey);
        btnGenerateKey.addActionListener(e -> generateKeyDialog.openDialog());

    }
}
