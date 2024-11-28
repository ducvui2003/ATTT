package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.HashFunctionController;
import nlu.fit.leanhduc.util.constraint.Hash;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileHashEvent;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileHashHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHashEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHashHandler;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashFunctionSection extends JPanel implements PanelTextHashEvent, PanelFileHashEvent {
    JPanel container;
    JComboBox<Hash> comboBoxHash;
    FileChooserButton btnLoadKey;
    JTextField textFieldKeyHMAC;
    JPanel panelKeyHMAC;
    JToggleButton checkBoxHMAC;
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler, panelFileHandler;
    HashFunctionController controller = HashFunctionController.getINSTANCE();

    public HashFunctionSection() {
        super();
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(container, BorderLayout.NORTH);
        this.btnLoadKey = new FileChooserButton("Chọn file", MetadataConfig.INSTANCE.getUploadIcon());
        this.comboBoxHash = new JComboBox<>(HashFunctionController.getINSTANCE().getHash().toArray(new Hash[0]));
        createKeyHMAC();
        createCheckBoxHMAC();
        createKeyPanel();
        createTabbedPane();
        handEvent();
    }

    private void createCheckBoxHMAC() {
        this.checkBoxHMAC = new JToggleButton();
        this.checkBoxHMAC.setSelected(false);
        this.panelKeyHMAC.setVisible(false);
        this.checkBoxHMAC.setPreferredSize(new Dimension(20, 20));
        this.checkBoxHMAC.setBackground(Color.DARK_GRAY);
    }

    private void handEvent() {
        this.checkBoxHMAC.addActionListener(e -> {
            this.checkBoxHMAC.setEnabled(true);
            this.panelKeyHMAC.setVisible(this.checkBoxHMAC.isSelected());
            this.checkBoxHMAC.setBackground(this.checkBoxHMAC.isSelected() ? Color.GREEN : Color.DARK_GRAY);
        });
        this.checkBoxHMAC.setEnabled(false);
        this.comboBoxHash.addActionListener(e -> {
            Hash selectedHash = (Hash) comboBoxHash.getSelectedItem();
            if (selectedHash == Hash.MD2) {
                this.checkBoxHMAC.setBackground(Color.DARK_GRAY);
                this.checkBoxHMAC.setEnabled(false);
                this.panelKeyHMAC.setVisible(false);
                return;
            }
            this.checkBoxHMAC.setEnabled(true);
            this.panelKeyHMAC.setVisible(this.checkBoxHMAC.isSelected());
            this.checkBoxHMAC.setBackground(this.checkBoxHMAC.isSelected() ? Color.GREEN : Color.DARK_GRAY);
        });
    }


    private void createKeyPanel() {
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(0, 10, 0, 0)
                        .build()
                , new JLabel("Chọn hàm băm:"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , comboBoxHash);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(0, 10, 0, 0)
                        .build()
                , new JLabel("HMAC"));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)
                        .anchor(GridBagConstraints.WEST)
                        .build()
                , checkBoxHMAC);

        JPanel panel = new JPanel(new GridBagLayout());

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(2, 0)
                        .weight(1, 1)
                        .gridSpan(2, 2)
                        .fill(GridBagConstraints.BOTH)
                        .build()
                , panel);

        SwingComponentUtil.addComponentGridBag(
                panel,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1, 1)
                        .insets(10, 10, 10, 10)
                        .fill(GridBagConstraints.BOTH)
                        .build()
                , this.panelKeyHMAC);
    }

    private void createKeyHMAC() {
        this.panelKeyHMAC = new JPanel(new GridBagLayout());
        this.textFieldKeyHMAC = new JTextField();
        SwingComponentUtil.addComponentGridBag(
                this.panelKeyHMAC,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1, 0)
                        .insets(5, 0, 5, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , new JLabel("Nhập khóa:"));

        SwingComponentUtil.addComponentGridBag(
                this.panelKeyHMAC,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , this.textFieldKeyHMAC);
    }

    private void createTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        this.panelTextHandler = new PanelTextHashHandler(this);
        this.panelFileHandler = new PanelFileHashHandler(this);
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Hash chuỗi", panelTextHandler);
        panelMap.put("Hash file", panelFileHandler);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)         
                        .gridSpan(15, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)  
                        .build(),
                this.tabbedPane);
    }

    @Override
    public String onHash(String plainText) {
        Hash currentHash = (Hash) comboBoxHash.getSelectedItem();
        String key = textFieldKeyHMAC.getText();
        if (currentHash == Hash.MD2) {
            return controller.hash(currentHash, null, plainText);
        }
        return controller.hash(currentHash, key, plainText);
    }

    @Override
    public String onHashFile(String filePath) {
        Hash currentHash = (Hash) comboBoxHash.getSelectedItem();
        String key = textFieldKeyHMAC.getText();
        if (currentHash == Hash.MD2) {
            return controller.hashFile(currentHash, null, filePath);
        }
        return controller.hashFile(currentHash, key, filePath);
    }
}
