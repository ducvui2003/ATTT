package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SubstitutionCipherController;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.key.*;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.*;
import nlu.fit.leanhduc.view.component.panel.key.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubstitutionCipherSection extends JPanel implements FileChooserEvent, ActionListener, PanelTextHandlerEvent {
    FileChooser fileChooser;
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Language> comboBoxLanguage;
    JTextField plainTextBlock, encryptBlock, decryptBlock;
    JButton btnCreateKey;
    KeyTypingPanel currentKeyTypingPanel;
    MainController controller;
    Cipher cipherCurrent;
    Language languageCurrent;
    JPanel panelTypeKey;
    final String commandEncrypt = "encrypt";
    final String commandDecrypt = "decrypt";
    final String commandComboBoxCipher = "comboBoxCipher";
    final String commandComboBoxLanguage = "comboBoxLanguage";
    final String commandCreateKey = "createKey";
    final KeyTypingPanel<ShiftKey> shiftKeyTypingPanel = new ShiftKeyTypingPanel(controller);
    final KeyTypingPanel<SubstitutionKey> substitutionKeyTypingPanel = new SubstitutionTypingPanel(controller);
    final KeyTypingPanel<AffineKey> affineKeyTypingPanel = new AffineKeyTypingPanel(controller);
    final KeyTypingPanel<ViginereKey> viginereKeyTypingPanel = new ViginereKeyTypingPanel(controller, languageCurrent);
    final KeyTypingPanel<HillKey> hillKeyTypingPanel = new HillCipherTyping(controller);
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler, panelFileHandler;
    GridBagConstraints gbc;
    JPanel container;

    public SubstitutionCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.gbc = new GridBagConstraints();
        this.container = new JPanel(new GridBagLayout());
        this.add(container, BorderLayout.NORTH);
        this.comboBoxCipher = new JComboBox<>(List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.VIGENERE, Cipher.HILL).toArray(new Cipher[0]));
        this.comboBoxLanguage = new JComboBox<>(Language.values());
        createKeyPanel();
        createTabbedPane();
    }

    private void createKeyPanel() {

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , new JLabel("Chọn thuật toán"));

        createComboBox();
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , comboBoxCipher);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(2, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , comboBoxLanguage);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(3, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , new JLabel("Tải khóa từ file"));

        this.fileChooser = new FileChooser();
        this.fileChooser.setEvent(this);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(4, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , fileChooser);

        this.btnCreateKey = new JButton("Tạo khóa");
        this.btnCreateKey.setActionCommand(commandCreateKey);
        this.btnCreateKey.addActionListener(this);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(5, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , btnCreateKey);


        currentKeyTypingPanel = new ShiftKeyTypingPanel(controller);
        createKeyTypingPanel();
    }

    private void createKeyTypingPanel() {
        panelTypeKey = new JPanel(new BorderLayout());
        panelTypeKey.add(currentKeyTypingPanel);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)        // Starting at the first column in the desired row
                        .gridSpan(6, 1)    // Span 5 columns
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                SwingComponentUtil.createSeparator());

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .gridSpan(6, 2)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , panelTypeKey);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)        // Starting at the first column in the desired row
                        .gridSpan(6, 1)    // Span 5 columns
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                SwingComponentUtil.createSeparator());
    }

    private void createComboBox() {
        this.comboBoxCipher.setActionCommand(commandComboBoxCipher);
        this.cipherCurrent = (Cipher) comboBoxCipher.getSelectedItem();
        this.comboBoxCipher.addActionListener(this);

        this.comboBoxLanguage.setActionCommand(commandComboBoxLanguage);
        this.languageCurrent = (Language) comboBoxLanguage.getSelectedItem();
        this.comboBoxLanguage.addActionListener(this);
        comboBoxLanguage.setToolTipText("Chọn ngôn ngữ để tạo khóa");
    }

    private void createTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        this.panelTextHandler = new PanelTextHandler(this);
        this.panelFileHandler = new PanelFileHandler();
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa Chuỗi", panelTextHandler);
        panelMap.put("Mã hóa File", panelFileHandler);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 5)        // Starting at the first column in the desired row
                        .gridSpan(6, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                this.tabbedPane);
    }

    @Override
    public boolean onBeforeFileSelected() {
        return true;
    }

    @Override
    public void onFileSelected(File file) {

    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case commandEncrypt:
                try {
                    String encrypt = SubstitutionCipherController.getINSTANCE().encrypt(plainTextBlock.getText(), currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
                    encryptBlock.setText(encrypt);
                } catch (CipherException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case commandDecrypt:
                try {
                    String decrypt = SubstitutionCipherController.getINSTANCE().decrypt(encryptBlock.getText(), currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
                    decryptBlock.setText(decrypt);
                } catch (CipherException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case commandComboBoxCipher:
                cipherCurrent = (Cipher) comboBoxCipher.getSelectedItem();
                assert cipherCurrent != null;
                replaceKeyTypingPanel(cipherCurrent, null);
                break;
            case commandComboBoxLanguage:
                languageCurrent = (Language) comboBoxLanguage.getSelectedItem();
            case commandCreateKey:
                IKeyGenerator<?> keyGenerator = controller.generateKey(cipherCurrent, languageCurrent);
                replaceKeyTypingPanel(cipherCurrent, keyGenerator);
            default:
                break;
        }
    }

    private void replaceKeyTypingPanel(Cipher cipher, IKeyGenerator keyGenerator) {
        panelTypeKey.removeAll();
        panelTypeKey.repaint();
        switch (cipher) {
            case SHIFT:
                currentKeyTypingPanel = shiftKeyTypingPanel;
                break;
            case SUBSTITUTION:
                currentKeyTypingPanel = substitutionKeyTypingPanel;
                break;
            case AFFINE:
                currentKeyTypingPanel = affineKeyTypingPanel;
                break;
            case VIGENERE:
                currentKeyTypingPanel = viginereKeyTypingPanel;
                break;
            case HILL:
                currentKeyTypingPanel = hillKeyTypingPanel;
                break;
        }
        panelTypeKey.add(currentKeyTypingPanel, BorderLayout.CENTER);
        if (keyGenerator != null) {
            currentKeyTypingPanel.setKey(keyGenerator.generateKey());
        }
    }

    @Override
    public String onEncrypt(String plainText) {
        try {
            String encrypt = SubstitutionCipherController.getINSTANCE().encrypt(plainText, currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
            return encrypt;
        } catch (CipherException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    @Override
    public String onDecrypt(String cipherText) {
        try {
            String decrypt = SubstitutionCipherController.getINSTANCE().decrypt(cipherText, currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
            return decrypt;
        } catch (CipherException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
}
