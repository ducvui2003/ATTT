package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.view.component.dialog.LoadKeyDialog;

import javax.swing.*;
import java.awt.*;

public class SymmetricEncryptionSection extends JPanel {
    Button btnLoadKey;
    LoadKeyDialog loadKeyDialog;

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
        btnLoadKey = new Button("Chọn khóa");
        panel.add(btnLoadKey);
        loadKeyDialog = new LoadKeyDialog((Frame) this.getParent());
        btnLoadKey.addActionListener(e -> loadKeyDialog.openDialog());
    }
}
