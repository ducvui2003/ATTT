package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTextHandler extends PanelHandler implements ActionListener {
    final String commandEncrypt = "encrypt";
    final String commandDecrypt = "decrypt";
    JTextField plainTextBlock, encryptBlock, decryptBlock;
    JButton btnEncrypt, btnDecrypt;
    protected PanelTextHandlerEvent event;

    public PanelTextHandler(PanelTextHandlerEvent event) {
        this.event = event;
    }

    @Override
    protected void init() {
        this.setLayout(new GridBagLayout());

        this.plainTextBlock = new JTextField(20);
        this.encryptBlock = new JTextField(20);
        this.decryptBlock = new JTextField(20);
        int leftMargin = 10;
        this.btnEncrypt = new JButton("Mã hóa");
        this.btnEncrypt.setActionCommand(commandEncrypt);
        this.btnEncrypt.addActionListener(this);

        this.btnDecrypt = new JButton("Giải mã");
        this.btnDecrypt.setActionCommand(commandDecrypt);
        this.btnDecrypt.addActionListener(this);


        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("Nhập văn bản"));

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
                new JLabel("Văn bản mã hóa"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)        // Starting at the first column in the desired row
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                encryptBlock);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản giải mã"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)        // Starting at the first column in the desired row
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                decryptBlock);

        JPanel panelContainBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelContainBtn.add(btnEncrypt);
        panelContainBtn.add(btnDecrypt);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 3)        // Starting at the first column in the desired row
                        .weight(0.5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                panelContainBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(commandEncrypt)) {
            String encryptText = this.event.onEncrypt(plainTextBlock.getText());
            this.encryptBlock.setText(encryptText);
        } else if (command.equals(commandDecrypt)) {
            String decryptText = this.event.onDecrypt(encryptBlock.getText());
            this.decryptBlock.setText(decryptText);
        }
    }
}
