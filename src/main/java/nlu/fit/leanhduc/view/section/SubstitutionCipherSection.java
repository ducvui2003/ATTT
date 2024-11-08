package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.key.IKeyDisplay;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.view.component.dialog.key.*;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class SubstitutionCipherSection extends JPanel implements FileChooserEvent, ActionListener {
    FileChooser fileChooser;
    JComboBox<Cipher> comboBoxCipher;
    JButton btnInputKey;
    JTextArea plainTextBlock, encryptBlock, decryptBlock;
    JButton btnEncrypt, btnDecrypt;
    KeyTypingPanel currentKeyTypingPanel;
    MainController controller;
    Cipher cipher;
    IKeyDisplay key;
    JPanel panelTypeKey;
    final String commandEncrypt = "encrypt";
    final String commandDecrypt = "decrypt";
    final String commandComboBoxCipher = "comboBoxCipher";
    final KeyTypingPanel shiftKeyTypingPanel = new ShiftKeyTypingPanel(controller);
    final KeyTypingPanel substitutionKeyTypingPanel = new SubstitutionTypingPanel(controller);
    final KeyTypingPanel affineKeyTypingPanel = new AffineKeyTypingPanel(controller);
    final KeyTypingPanel viginereKeyTypingPanel = new ViginereKeyTypingPanel(controller);
    final KeyTypingPanel hillKeyTypingPanel = new HillCipherTyping(controller);

    public SubstitutionCipherSection(MainController controller) {
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new BorderLayout(5, 5));
        this.comboBoxCipher = new JComboBox<>(
                List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.VIGENERE, Cipher.HILL)
                        .toArray(new Cipher[0]));
        createKeyPanel();
        createViewProcess();
    }

    private void createKeyPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        this.add(panel, BorderLayout.NORTH);
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.add(panelTop, BorderLayout.NORTH);
        panelTop.add(new JLabel("Chọn thuật toán"));
        createComboBox();
        panelTop.add(comboBoxCipher);

        this.fileChooser = new FileChooser();
        this.fileChooser.setEvent(this);
        panelTop.add(new JLabel("Tải khóa từ file"));
        panelTop.add(fileChooser);

        currentKeyTypingPanel = new ShiftKeyTypingPanel(controller);
        createKeyTypingPanel(panel);
    }

    private void createKeyTypingPanel(JPanel parent) {
        panelTypeKey = new JPanel();
        panelTypeKey.add(currentKeyTypingPanel, BorderLayout.CENTER);
        panelTypeKey.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        parent.add(panelTypeKey, BorderLayout.CENTER);
    }

    private void createComboBox() {
        comboBoxCipher.setActionCommand(commandComboBoxCipher);
        this.cipher = (Cipher) comboBoxCipher.getSelectedItem();
        this.comboBoxCipher.addActionListener(this);
    }


    private void createViewProcess() {
        JPanel panel = new JPanel(new GridBagLayout());
        this.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;


        createLabel(panel, "Nhập văn bản", gbc, 0);
        createLabel(panel, "Văn bản mã hóa", gbc, 2);
        createLabel(panel, "Văn bản giải mã", gbc, 4);

        this.plainTextBlock = createTopLeftAlignedTextArea();
        this.encryptBlock = createTopLeftAlignedTextArea();
        this.decryptBlock = createTopLeftAlignedTextArea();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 4;
        panel.add(plainTextBlock, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(encryptBlock, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        panel.add(decryptBlock, gbc);

        this.btnEncrypt = new JButton("Mã hóa");
        this.btnEncrypt.setActionCommand(commandEncrypt);
        this.btnDecrypt = new JButton("Giải mã");
        this.btnDecrypt.setActionCommand(commandDecrypt);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnEncrypt, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(btnDecrypt, gbc);
    }

    private JTextArea createTopLeftAlignedTextArea() {
        JTextArea textArea = new JTextArea(1, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(200, 80));
        textArea.setMargin(new Insets(5, 5, 5, 5));
        return textArea;
    }

    private void createLabel(JPanel panel, String text, GridBagConstraints gbc, int gridx) {
        JLabel label = new JLabel(text);
        gbc.gridx = gridx;
        gbc.gridheight = 1;
        panel.add(label, gbc);
    }


    private void handleTypeKey() {
        this.btnInputKey.addActionListener(this);
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
                break;
            case commandDecrypt:
                break;
            case commandComboBoxCipher:
                cipher = (Cipher) comboBoxCipher.getSelectedItem();
                assert cipher != null;
                replaceKeyTypingPanel(cipher);
                break;
            default:
                break;
        }
    }

    private void replaceKeyTypingPanel(Cipher cipher) {
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
    }
}
