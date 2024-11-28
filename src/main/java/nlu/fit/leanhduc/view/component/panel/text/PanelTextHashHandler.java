package nlu.fit.leanhduc.view.component.panel.text;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTextHashHandler extends PanelHandler implements ActionListener {
    final String commandHash = "hash";
    JTextField plainTextBlock, hashBlock;
    JButton btnHash;
    PanelTextHashEvent event;

    public PanelTextHashHandler(PanelTextHashEvent event) {
        this.event = event;
    }

    @Override
    protected void init() {
        this.setLayout(new GridBagLayout());

        this.plainTextBlock = new JTextField(20);
        this.hashBlock = new JTextField(20);
        int leftMargin = 10;
        this.btnHash = new JButton("Hash");
        this.btnHash.setActionCommand(commandHash);
        this.btnHash.addActionListener(this);


        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)         
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0)  
                        .build(),
                new JLabel("Nhập văn bản"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)         
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
                        .build(),
                plainTextBlock);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)         
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, leftMargin, 10, 0)  
                        .build(),
                new JLabel("Văn bản đã hash"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)         
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
                        .build(),
                hashBlock);
        JPanel panelContainBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelContainBtn.add(btnHash);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)         
                        .weight(0.5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                panelContainBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(commandHash)) {
            hashBlock.setText(event.onHash(plainTextBlock.getText()));
        }
    }
}
