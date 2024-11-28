package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class PanelFileSign extends JPanel implements FileChooserEvent {
    FileChooser chooseFileData;
    JTextArea signatureTextBlock;
    JPanel container;
    JButton btnSign;
    PanelFileSignEvent event;
    String src;

    public PanelFileSign(PanelFileSignEvent event) {
        this.event = event;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);
        this.chooseFileData = new FileChooser();
        this.chooseFileData.setEvent(this);
        this.btnSign = new JButton("Ký", null);
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
                new JLabel("File cần ký"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1.5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                chooseFileData);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)         
                        .weight(0.25, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)  
                        .build(),
                new JLabel("Chữ ký"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)         
                        .weight(1, 1)
                        .gridSpan(0, 1)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10)  
                        .build(),
                signatureTextBlock);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .weight(1.0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
                        .build(),
                btnSign);

        setEventClipBoard();
        setEventSigned();
    }

    public void setEventClipBoard() {
        this.signatureTextBlock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!signatureTextBlock.getText().isBlank()) {
                    String textToCopy = signatureTextBlock.getText();
                    StringSelection stringSelection = new StringSelection(textToCopy);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);

                    JOptionPane.showMessageDialog(null, "Copy chữ vào clipboard!");
                }
            }
        });
    }

    public void setEventSigned() {
        this.btnSign.addActionListener(e -> {
            String signature = this.event.onSignFile(src);
            this.signatureTextBlock.setText(signature);
        });
    }

    @Override
    public void onFileSelected(File file) {
        this.src = this.chooseFileData.getPath();
    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }
}
