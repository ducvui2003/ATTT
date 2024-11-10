package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.view.component.dialog.GenerateKeyDialog;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SymmetricCipherSection extends JPanel implements ActionListener {
    MainController controller;
    List<CipherSpecification> cipherSpecifications = List.of(CipherSpecification.AES, CipherSpecification.DES, CipherSpecification.TRIPLEDES);
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Integer> comboBoxKeySize;
    JComboBox<String> comboBoxIVSize;
    Cipher currentCipher;
    Mode currentMode;
    Padding currentPadding;
    Integer currentKeySize;
    Integer currentIVSize;
    JButton btnGenerateKey;
    JTextArea plainTextBlock, encryptBlock, decryptBlock;
    JButton btnEncrypt, btnDecrypt;
    JPanel fileChooser, downloadChooser;


    public SymmetricCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

        // Initialize Cipher ComboBox and Listener
        this.comboBoxCipher = new JComboBox<>(cipherSpecifications.stream()
                .map(CipherSpecification::getAlgorithm)
                .toArray(Cipher[]::new));
        this.comboBoxCipher.addActionListener(e -> updateModeAndPadding());

        // Initialize Mode ComboBox and Listener
        this.comboBoxMode = new JComboBox<>();
        this.comboBoxMode.addActionListener(e -> updatePaddingAndIvSize());

        // Initialize Padding ComboBox and Listener
        this.comboBoxPadding = new JComboBox<>();
        this.comboBoxPadding.addActionListener(e -> updateIvSize());

        // Initialize Key Size ComboBox and Listener
        this.comboBoxKeySize = new JComboBox<>();
        this.comboBoxKeySize.addActionListener(e -> handleKeySizeChange());

        // Initialize IV Size ComboBox (No listener needed)
        this.comboBoxIVSize = new JComboBox<>();

        // Add Components to Panel
        this.add(new JLabel("Cipher:"));
        this.add(this.comboBoxCipher);
        this.add(new JLabel("Mode:"));
        this.add(this.comboBoxMode);
        this.add(new JLabel("Padding:"));
        this.add(this.comboBoxPadding);
        this.add(new JLabel("Key Size:"));
        this.add(this.comboBoxKeySize);
        this.add(new JLabel("IV Size:"));
        this.add(this.comboBoxIVSize);

        this.btnGenerateKey = new JButton("Generate Key");
        this.btnGenerateKey.addActionListener(this);
        this.add(this.btnGenerateKey);

        // Initialize dependent fields
        updateModeAndPadding();
        JPanel panelCenter = createTextCipherPanel();
        this.add(panelCenter, BorderLayout.CENTER);
        JPanel panelSouth = createFileCipherPanel();
        this.add(panelSouth, BorderLayout.SOUTH);
    }


    private JPanel createTextCipherPanel() {
        JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        JPanel panel1 = createTopLeftAlignedTextArea("Plain Text", this.plainTextBlock);
        JPanel panel2 = createTopLeftAlignedTextArea("Encrypt", this.encryptBlock);
        JPanel panel3 = createTopLeftAlignedTextArea("Decrypt", this.decryptBlock);
        panelCenter.add(panel1);
        this.btnEncrypt = new JButton("Mã hóa");
        panelCenter.add(this.btnEncrypt);
        panelCenter.add(panel2);
        this.btnDecrypt = new JButton("Giải mã");
        panelCenter.add(this.btnDecrypt);
        panelCenter.add(panel3);
        return panelCenter;
    }

    private JPanel createFileCipherPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel panel1 = new JPanel();
        panel.add(panel1, BorderLayout.EAST);
        panel1.add(new JLabel("Chọn file"));
        this.fileChooser = new FileChooser();
        panel1.add(this.fileChooser);

        Border combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Mã hóa file")
        );
        panel1.setBorder(combinedBorder);

        JPanel panel2 = new JPanel();
        panel.add(panel2, BorderLayout.WEST);
        panel2.add(new JLabel("Chọn file"));
        this.downloadChooser = new FileChooser();
        panel2.add(this.downloadChooser);

        combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Giải mã file")
        );
        panel2.setBorder(combinedBorder);
        return panel;
    }

    private JPanel createTopLeftAlignedTextArea(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        textArea = new JTextArea(1, 20);
        panel.add(new JLabel(title), BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(200, 80));
        textArea.setMargin(new Insets(5, 5, 5, 5));
        return panel;
    }


    // Update Mode and Padding when Cipher changes
    private void updateModeAndPadding() {
        Cipher selectedCipher = (Cipher) this.comboBoxCipher.getSelectedItem();
        CipherSpecification selectedSpec = getCipherSpecification(selectedCipher);
        this.currentCipher = selectedCipher;

        // Temporarily remove mode listener to avoid triggering it during updates
        ActionListener modeListener = this.comboBoxMode.getActionListeners()[0];
        this.comboBoxMode.removeActionListener(modeListener);

        // Update Mode ComboBox
        this.comboBoxMode.removeAllItems();
        selectedSpec.getValidModePaddingCombinations().keySet().forEach(mode -> this.comboBoxMode.addItem(mode));
        this.currentMode = (Mode) this.comboBoxMode.getSelectedItem();

        // Re-add the mode listener
        this.comboBoxMode.addActionListener(modeListener);

        // Update dependent ComboBoxes
        updatePaddingAndIvSize();
        updateKeySize();
        updateIvSize();
    }

    // Update Padding and IV Size when Mode changes
    private void updatePaddingAndIvSize() {
        Cipher selectedCipher = (Cipher) this.comboBoxCipher.getSelectedItem();
        CipherSpecification selectedSpec = getCipherSpecification(selectedCipher);
        this.currentMode = (Mode) this.comboBoxMode.getSelectedItem();

        // Temporarily remove padding listener to avoid triggering it during updates
        ActionListener paddingListener = this.comboBoxPadding.getActionListeners()[0];
        this.comboBoxPadding.removeActionListener(paddingListener);

        // Update Padding ComboBox
        this.comboBoxPadding.removeAllItems();
        if (this.currentMode != null) {
            selectedSpec.getValidModePaddingCombinations().get(this.currentMode)
                    .forEach(padding -> this.comboBoxPadding.addItem(padding));
        }
        this.currentPadding = (Padding) this.comboBoxPadding.getSelectedItem();

        // Re-add the padding listener
        this.comboBoxPadding.addActionListener(paddingListener);

        // Update IV Size based on Mode
        updateIvSize();
    }

    // Update Key Size based on selected Cipher
    private void updateKeySize() {
        CipherSpecification selectedSpec = getCipherSpecification(this.currentCipher);
        this.comboBoxKeySize.removeAllItems();
        selectedSpec.getSupportedKeySizes().forEach(size -> this.comboBoxKeySize.addItem(size));
        this.currentKeySize = (Integer) this.comboBoxKeySize.getSelectedItem();
    }

    // Update IV Size based on selected Mode
    private void updateIvSize() {
        CipherSpecification selectedSpec = getCipherSpecification(this.currentCipher);
        Mode selectedMode = (Mode) this.comboBoxMode.getSelectedItem();

        this.comboBoxIVSize.removeAllItems();
        Integer ivSize = selectedSpec.getIvSizes().get(selectedMode);
        this.currentIVSize = ivSize;
        if (ivSize != null) {
            this.comboBoxIVSize.addItem(ivSize + " bits");
        } else {
            this.comboBoxIVSize.addItem("None");
        }
    }

    private void handleKeySizeChange() {
        Integer selectedKeySize = (Integer) this.comboBoxKeySize.getSelectedItem();
        System.out.println("Selected Key Size: " + selectedKeySize);
    }


    private CipherSpecification getCipherSpecification(Cipher cipher) {
        switch (cipher) {
            case AES:
                return CipherSpecification.AES;
            case DES:
                return CipherSpecification.DES;
            case DESEDE:
                return CipherSpecification.TRIPLEDES;
            default:
                throw new IllegalArgumentException("Unknown cipher: " + cipher);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerateKey) {
            System.out.println("Generating key...");
            System.out.println(this.currentCipher.getName() + "/" + this.currentMode.getName() + "/" + this.currentPadding.getName());
            System.out.println("Key Size: " + this.currentKeySize);
            System.out.println("IV Size: " + this.currentIVSize);
        }
    }
}