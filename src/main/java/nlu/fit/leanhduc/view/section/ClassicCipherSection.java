package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SubstitutionCipherController;
import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.key.classic.*;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.key.*;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandlerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Security;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassicCipherSection extends JPanel implements ActionListener, PanelTextHandlerEvent, FileChooserEvent {
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Language> comboBoxLanguage;
    JButton btnCreateKey;
    KeyTypingPanel currentKeyTypingPanel;
    MainController controller;
    Cipher cipherCurrent;
    Language languageCurrent;
    JPanel panelTypeKey;
    final String commandComboBoxCipher = "comboBoxCipher";
    final String commandComboBoxLanguage = "comboBoxLanguage";
    final String commandCreateKey = "createKey";
    final String commandLoadKey = "LoadKey";
    final KeyTypingPanel<ShiftKeyClassic> shiftKeyTypingPanel = new ShiftKeyTypingPanel(controller);
    final KeyTypingPanel<SubstitutionKeyClassic> substitutionKeyTypingPanel = new SubstitutionKeyTypingPanel(controller);
    final KeyTypingPanel<AffineKeyClassic> affineKeyTypingPanel = new AffineKeyTypingPanel(controller);
    final KeyTypingPanel<ViginereKeyClassic> viginereKeyTypingPanel = new ViginereKeyTypingPanel(controller, languageCurrent);
    final KeyTypingPanel<HillKeyClassic> hillKeyTypingPanel = new HillKeyTypingPanel(controller);
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler;
    GridBagConstraints gbc;
    JPanel container;
    FileChooserButton btnLoadKey;

    public ClassicCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.gbc = new GridBagConstraints();
        this.container = new JPanel(new GridBagLayout());
        this.add(container, BorderLayout.NORTH);
        this.comboBoxCipher = new JComboBox<>(List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.VIGINERE, Cipher.HILL).toArray(new Cipher[0]));
        this.comboBoxLanguage = new JComboBox<>(Language.values());
        this.btnLoadKey = new FileChooserButton("Chọn file", MetadataConfig.INSTANCE.getUploadIcon());
        this.btnLoadKey.setEvent(this);
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

//        SwingComponentUtil.addComponentGridBag(s
//                this.container,
//                GridBagConstraintsBuilder.builder()
//                        .grid(3, 0)
//                        .weight(1.0, 0.0)
//                        .fill(GridBagConstraints.HORIZONTAL)
//                        .build()
//                , new JLabel("Tải khóa từ file"));
//
//
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(4, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build()
                , btnLoadKey);

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
                        .grid(0, 1)
                        .gridSpan(6, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
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
                        .grid(0, 4)
                        .gridSpan(6, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
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
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa Chuỗi", panelTextHandler);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 5)
                        .gridSpan(6, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0)
                        .build(),
                this.tabbedPane);
    }

    @Override
    public boolean onBeforeFileSelected() {
        return true;
    }

    @Override
    public void onFileSelected(File file) {
        try {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
            case commandComboBoxCipher:
                cipherCurrent = (Cipher) comboBoxCipher.getSelectedItem();
                assert cipherCurrent != null;
                replaceKeyTypingPanel(cipherCurrent, null);
                break;
            case commandComboBoxLanguage:
                languageCurrent = (Language) comboBoxLanguage.getSelectedItem();
            case commandCreateKey:
                ICipher<?> keyGenerator = controller.generateKey(cipherCurrent, languageCurrent);
                replaceKeyTypingPanel(cipherCurrent, keyGenerator);
            case commandLoadKey:
                break;
            default:
                break;
        }
    }

    private void replaceKeyTypingPanel(Cipher cipher, ICipher keyGenerator) {
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
            case VIGINERE:
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
    public boolean canEncrypt(String plainText) {
        if (plainText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập văn bản cần mã hóa", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }


    @Override
    public boolean canDecrypt(String encryptText) {
        if (encryptText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập văn bản cần giải mã", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public String onEncrypt(String plainText) {
        try {
            return SubstitutionCipherController.getINSTANCE().encrypt(plainText, currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
        } catch (CipherException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String onDecrypt(String cipherText) {
        try {
            return SubstitutionCipherController.getINSTANCE().decrypt(cipherText, currentKeyTypingPanel.getKey(), cipherCurrent, languageCurrent);
        } catch (CipherException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

}
