package nlu.fit.leanhduc.view.component.panel.text;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTextSign extends JPanel implements ActionListener {
    JTextArea plainTextBlock, signedTextBlock;
    JButton btnSign;
    PanelTextSignEvent event;

    public PanelTextSign(PanelTextSignEvent event) {
        this.event = event;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new GridBagLayout());
        this.plainTextBlock = SwingComponentUtil.createTextArea();
        this.plainTextBlock.setEnabled(true);
        this.signedTextBlock = SwingComponentUtil.createTextArea();
        this.signedTextBlock.setEnabled(true);
        this.signedTextBlock.setEditable(false);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JLabel("Văn bản cần ký "));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)        // Starting at the first column in the desired row
                        .weight(1.0, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JScrollPane(plainTextBlock));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)        // Starting at the first column in the desired row
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                new JLabel("Chữ ký"));

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)        // Starting at the first column in the desired row
                        .weight(1, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10) // Optional padding around the separator
                        .build(),
                signedTextBlock);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.btnSign = new JButton("Ký");
        this.btnSign.addActionListener(this);
        panel.add(btnSign);

        SwingComponentUtil.addComponentGridBag(
                this,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)        // Starting at the first column in the desired row
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10) // Optional padding around the separator
                        .build(),
                panel);

        setEventClipBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (event != null) {
            String textSigned = event.onSign(plainTextBlock.getText());
            this.signedTextBlock.setText(textSigned);
        }
    }

    public void setEventClipBoard() {
        this.signedTextBlock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!signedTextBlock.getText().isBlank()) {
                    // Copy the text to the clipboard
                    String textToCopy = signedTextBlock.getText();
                    StringSelection stringSelection = new StringSelection(textToCopy);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);

                    // Optional: Show a confirmation message
                    JOptionPane.showMessageDialog(null, "Copy chữ vào clipboard!");
                }
            };
        });
    }
}
