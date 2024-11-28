package nlu.fit.leanhduc.view.component.panel.text;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTextVerify extends JPanel implements ActionListener {
    JTextArea dataVerifyTextBlock, signatureTextBlock;
    JButton btnVerify;
    PanelTextVerifyEvent event;

    public PanelTextVerify(PanelTextVerifyEvent event) {
        this.event = event;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new GridBagLayout());
        this.dataVerifyTextBlock = SwingComponentUtil.createTextArea();
        this.dataVerifyTextBlock.setEnabled(true);
        this.signatureTextBlock = SwingComponentUtil.createTextArea();
        this.signatureTextBlock.setEnabled(true);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)         
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)  
                        .build(),
                new JLabel("Văn bản"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)         
                        .weight(1.0, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10)  
                        .build(),
                new JScrollPane(dataVerifyTextBlock));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                new JLabel("Chữ ký"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)         
                        .weight(1.0, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10)  
                        .build(),
                new JScrollPane(signatureTextBlock));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.btnVerify = new JButton("Xác thực");
        this.btnVerify.addActionListener(this);
        panel.add(btnVerify);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)         
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)  
                        .build(),
                panel);
    }

    private String getSignature() {
        return this.signatureTextBlock.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (event != null) {
            event.onVerify(dataVerifyTextBlock.getText(), getSignature());
        }
    }
}
