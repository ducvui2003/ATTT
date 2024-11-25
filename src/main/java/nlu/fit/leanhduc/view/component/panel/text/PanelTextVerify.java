package nlu.fit.leanhduc.view.component.panel.text;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTextVerify extends JPanel implements ActionListener {
    JTextArea signedTextBlock, verifyTextBlock;
    JButton btnVerify;
    PanelTextVerifyEvent event;

    public PanelTextVerify(PanelTextVerifyEvent event) {
        this.event = event;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new GridBagLayout());
        this.signedTextBlock = SwingComponentUtil.createTextArea();
        this.signedTextBlock.setEnabled(true);
        this.verifyTextBlock = SwingComponentUtil.createTextArea();
        this.verifyTextBlock.setEnabled(true);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản đã ký"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)        // Starting at the first column in the desired row
                        .weight(1.0, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JScrollPane(signedTextBlock));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản gốc"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)        // Starting at the first column in the desired row
                        .weight(1, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10) // Optional padding around the separator
                        .build(),
                verifyTextBlock);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.btnVerify = new JButton("Xác thực");
        this.btnVerify.addActionListener(this);
        panel.add(btnVerify);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)        // Starting at the first column in the desired row
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (event != null) {
            event.onVerify(signedTextBlock.getText());
        }
    }
}
