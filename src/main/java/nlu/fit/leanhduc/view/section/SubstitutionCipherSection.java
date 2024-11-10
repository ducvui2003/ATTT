package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SubstitutionCipherController;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.key.*;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubstitutionCipherSection extends JPanel implements FileChooserEvent, ActionListener {
    FileChooser fileChooser;
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Language> comboBoxLanguage;
    JButton btnInputKey;
    JTextField plainTextBlock, encryptBlock, decryptBlock;
    JButton btnEncrypt, btnDecrypt, btnCreateKey;
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
    final KeyTypingPanel<ViginereKey> viginereKeyTypingPanel = new ViginereKeyTypingPanel(controller);
    final KeyTypingPanel<HillKey> hillKeyTypingPanel = new HillCipherTyping(controller);
    JPanel fileEncrypt, fileDecrypt;
    JTabbedPane tabbedPane;
    JPanel panelText, panelFile;
    GridBagConstraints gbc;

    public SubstitutionCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        this.comboBoxCipher = new JComboBox<>(List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.VIGENERE, Cipher.HILL).toArray(new Cipher[0]));
        this.comboBoxLanguage = new JComboBox<>(Language.values());
        createKeyPanel();
        createTabbedPane();

    }

    private void createKeyPanel() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        this.add(panel, gbc);
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.add(panelTop, BorderLayout.NORTH);
        panelTop.add(new JLabel("Chọn thuật toán"));
        createComboBox();
        panelTop.add(comboBoxCipher);
        panelTop.add(comboBoxLanguage);

        this.fileChooser = new FileChooser();
        this.fileChooser.setEvent(this);
        panelTop.add(new JLabel("Tải khóa từ file"));
        panelTop.add(fileChooser);

        btnCreateKey = new JButton("Tạo khóa");
        btnCreateKey.setActionCommand(commandCreateKey);
        btnCreateKey.addActionListener(this);
        panelTop.add(btnCreateKey);

        currentKeyTypingPanel = new ShiftKeyTypingPanel(controller);
        createKeyTypingPanel();
    }

    private void createKeyTypingPanel() {
        panelTypeKey = new JPanel(new BorderLayout());
        panelTypeKey.add(currentKeyTypingPanel);
        panelTypeKey.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true), BorderFactory.createEmptyBorder(5, 0, 0, 5)));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(panelTypeKey, gbc);
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
        createPanelText();
        createPanelFile();
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa Chuỗi", panelText);
        panelMap.put("Mã hóa File", panelFile);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(tabbedPane, gbc);
    }

    private void createPanelText() {
        this.plainTextBlock = new JTextField(20);
        this.encryptBlock = new JTextField(20);
        this.decryptBlock = new JTextField(20);
        int leftMargin = 10;

        panelText = new JPanel();
        this.panelText.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        this.panelText.add(createJLabelText("Nhập văn bản"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.panelText.add(plainTextBlock, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        this.panelText.add(createJLabelText("Văn bản mã hóa"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.panelText.add(encryptBlock, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        this.panelText.add(createJLabelText("Văn bản giải mã"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.panelText.add(decryptBlock, gbc);


        JPanel panelContainBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.btnEncrypt = new JButton("Mã hóa");
        this.btnEncrypt.setActionCommand(commandEncrypt);
        this.btnEncrypt.addActionListener(this);
        this.panelText.add(btnEncrypt, gbc);

        this.btnDecrypt = new JButton("Giải mã");
        this.btnDecrypt.setActionCommand(commandDecrypt);
        this.panelText.add(panelContainBtn, gbc);
        this.btnDecrypt.addActionListener(this);
        panelContainBtn.add(btnEncrypt);
        panelContainBtn.add(btnDecrypt);

    }

    private void createPanelFile() {
        panelFile = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel panel1 = new JPanel();
        panelFile.add(panel1);
        this.fileEncrypt = new FileChooser();
        panel1.add(this.fileEncrypt);

        Border combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Mã hóa file"));
        panel1.setBorder(combinedBorder);

        JPanel panel2 = new JPanel();
        panelFile.add(panel2);
        this.fileDecrypt = new FileChooser();
        panel2.add(this.fileDecrypt);

        combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Giải mã file"));
        panel2.setBorder(combinedBorder);
    }

    private static JLabel createJLabelText(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left within the Box
        return label;
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
                Language selectedItem = (Language) comboBoxLanguage.getSelectedItem();
                languageCurrent = selectedItem;
                System.out.println("You selected: " + selectedItem);
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
}
