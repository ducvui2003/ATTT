package nlu.fit.leanhduc.view.component.dialog;


import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.key.IKeyDisplay;
import nlu.fit.leanhduc.service.key.SubstitutionKey;
import nlu.fit.leanhduc.service.key.ViginereKey;
import nlu.fit.leanhduc.util.FileUtil;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.section.SubstitutionCipherSection;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GenerateKeyDialog extends CustomDialog implements ActionListener {
    JTextField inputKeyLength;
    TextArea textArea;
    FileChooser fileChooser;
    Language language;
    Cipher cipher;
    JPanel panelModePadding;
    Mode mode;
    Padding padding;
    private JPanel panelGenerateKey;
    private IKeyDisplay keyDisplay;

    List<Cipher> cipherHasModeAndPadding;
    List<Cipher> cipherSupportKeyLength;
    List<Cipher> cipherSupport2Language;

    // JComboBox
    JComboBox<Language> comboBoxLanguage;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Cipher> comboBoxCipher;
    MainController controller;
    JButton btnCreate;
    SubstitutionCipherSection parent;

    public GenerateKeyDialog(Frame owner, MainController controller, SubstitutionCipherSection panel) {
        super(owner, "Tạo khóa mới", Dialog.ModalityType.APPLICATION_MODAL);
        this.controller = controller;
        this.parent = panel;
    }

    @Override
    public void init() {
        this.cipherHasModeAndPadding = List.of(Cipher.AES, Cipher.DES, Cipher.BLOWFISH, Cipher.RSA);
        this.cipherSupportKeyLength = List.of(Cipher.VIGENERE);
        this.cipherSupport2Language = List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.HILL, Cipher.VIGENERE);
        this.comboBoxLanguage = new JComboBox<>(Language.values());
        this.comboBoxMode = new JComboBox<>(Mode.values());
        this.comboBoxPadding = new JComboBox<>(Padding.values());
        this.comboBoxCipher = new JComboBox<>(Cipher.values());

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        createPanelGenerateKey();
        createBtnCreate();
        createTextArea();
        this.setSize(600, 400);
    }

    private void createPanelGenerateKey() {
        JPanel panelWrapper = new JPanel();
        panelWrapper.setLayout(new BorderLayout(5, 5));
        this.add(panelWrapper);
        panelGenerateKey = new JPanel();
        panelGenerateKey.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelWrapper.add(panelGenerateKey, BorderLayout.CENTER);

        Border combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Thông tin khóa")
        );

        panelWrapper.setBorder(combinedBorder);

        panelModePadding = new JPanel();
        panelModePadding.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelWrapper.add(panelModePadding, BorderLayout.SOUTH);
        createComboBox();
        createKeyLengthInput();
    }

    private void createComboBox() {
//        Cipher
        this.cipher = (Cipher) comboBoxCipher.getSelectedItem();
        comboBoxCipher.addActionListener(e -> {
            Cipher selectedItem = (Cipher) comboBoxCipher.getSelectedItem();
            cipher = selectedItem;
            System.out.println("You selected: " + selectedItem);
            disableKeyLengthInput();
            disableModePadding();
            disableLanguage();
        });
        panelGenerateKey.add(comboBoxCipher);

//        Language
        this.language = (Language) comboBoxLanguage.getSelectedItem();
        comboBoxLanguage.addActionListener(e -> {
            Language selectedItem = (Language) comboBoxLanguage.getSelectedItem();
            language = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
        comboBoxLanguage.setToolTipText("Chọn ngôn ngữ để tạo khóa");
        panelGenerateKey.add(comboBoxLanguage);

//        Mode
        this.mode = (Mode) comboBoxMode.getSelectedItem();
        comboBoxMode.addActionListener(e -> {
            Mode selectedItem = (Mode) comboBoxMode.getSelectedItem();
            this.mode = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
        panelModePadding.add(comboBoxMode);

//        Padding
        this.padding = (Padding) comboBoxPadding.getSelectedItem();
        comboBoxPadding.addActionListener(e -> {
            Padding selectedItem = (Padding) comboBoxPadding.getSelectedItem();
            this.padding = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
        panelModePadding.add(comboBoxPadding);

        disableKeyLengthInput();
        disableModePadding();
        disableLanguage();
    }

    private void createKeyLengthInput() {
        inputKeyLength = SwingComponentUtil.createFormatTextFieldNumber(
                3, 1, 26, 1,
                false, true);
        inputKeyLength.setEnabled(false);
        panelGenerateKey.add(new JLabel("Độ dài khóa:"));
        panelGenerateKey.add(inputKeyLength);
    }

    public void createBtnCreate() {
        btnCreate = new JButton("Tạo khóa");
        btnCreate.addActionListener(e -> {

        });
    }

    private void createTextArea() {
        JPanel panel = new JPanel(new BorderLayout());
        Border combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Khóa mới:")
        );
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setColumns(50);
        textArea.setRows(20);
        panel.add(textArea);
        panel.setBorder(combinedBorder);
        this.add(panel, BorderLayout.CENTER);
    }

    private void disableKeyLengthInput() {
        if (inputKeyLength != null)
            inputKeyLength.setEnabled(this.cipherSupportKeyLength.contains(cipher));
    }

    private void disableModePadding() {
        comboBoxPadding.setEnabled(this.cipherHasModeAndPadding.contains(cipher));
        comboBoxMode.setEnabled(this.cipherHasModeAndPadding.contains(cipher));
    }

    private void disableLanguage() {
        comboBoxLanguage.setEnabled(this.cipherSupport2Language.contains(cipher));
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnCreate) {
            if (cipher == Cipher.VIGENERE) {
                Integer length = (Integer) ((JFormattedTextField) inputKeyLength).getValue();
                if (length == null) {
                    JOptionPane.showMessageDialog(this, "Độ dài khóa không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                IKeyGenerator<ViginereKey> keyGenerator = this.controller.generateKey(language, length);
                textArea.setText(keyGenerator.generateKey().display());
                return;
            }
            IKeyGenerator<?> keyGenerator = controller.generateKey(cipher, language);
            if (keyGenerator.generateKey() instanceof IKeyDisplay keyDisplay) {
                textArea.setText(keyDisplay.display());
            }
            if (keyGenerator.generateKey() instanceof SecretKey secretKey) {
                try {
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Không thể lưu file", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
