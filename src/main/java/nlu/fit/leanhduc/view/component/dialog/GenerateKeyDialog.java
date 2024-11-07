package nlu.fit.leanhduc.view.component.dialog;


import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.service.cipher.symmetric.vigenere.VigenereCipher;
import nlu.fit.leanhduc.service.key.IKeyDisplay;
import nlu.fit.leanhduc.util.*;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

public class GenerateKeyDialog extends CustomDialog implements FileChooserEvent {
    JTextField inputKeyLength;
    TextArea textArea;
    FileChooser fileChooser;
    Language language;
    Cipher cipher;
    Object key;
    JPanel panelModePadding;
    Mode mode;
    Padding padding;
    private JPanel panelGenerateKey;

    List<Cipher> cipherHasModeAndPadding;
    List<Cipher> cipherSupportKeyLength;
    List<Cipher> cipherSupport2Language;

    // JComboBox
    JComboBox<Language> comboBoxLanguage;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Cipher> comboBoxCipher;

    public GenerateKeyDialog(Frame owner) {
        super(owner, "Tạo khóa mới", Dialog.ModalityType.APPLICATION_MODAL);
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
        createFileOutput();
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
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(1);
        numberFormatter.setMaximum(26);
        numberFormatter.setCommitsOnValidEdit(true);

        inputKeyLength = new JFormattedTextField(numberFormatter);
        inputKeyLength.setColumns(3);
        inputKeyLength.setEnabled(false);
        ((JFormattedTextField) inputKeyLength).setValue(1);
        panelGenerateKey.add(new JLabel("Độ dài khóa:"));
        panelGenerateKey.add(inputKeyLength);
    }

    public void createFileOutput() {
        fileChooser = new FileChooser(true, "Tạo khóa");
        fileChooser.setEvent(this);
        panelGenerateKey.add(fileChooser);
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
    public void onFileSelected(File file) {
        key = Objects.requireNonNull(KeyGeneratorFactory.getKeyGenerator(cipher, language));
        if (key instanceof VigenereCipher vigenereCipher) {
            vigenereCipher.setKeyLength((Integer) ((JFormattedTextField) inputKeyLength).getValue());
            textArea.setText(vigenereCipher.generateKey().display());
            return;
        }
        IKeyGenerator<?> keyGenerator = (IKeyGenerator<?>) key;
        if (keyGenerator.generateKey() instanceof IKeyDisplay keyDisplay) {
            textArea.setText(keyDisplay.display());
            try {
                FileUtil.writeStringToFile(file.getAbsolutePath(), textArea.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không thể lưu file", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (keyGenerator.generateKey() instanceof SecretKey secretKey) {
            FileUtil.saveKeyToFile(secretKey, file.getAbsolutePath());
        }
    }


    @Override
    public void onFileUnselected() {
        // Do nothing
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public boolean onBeforeFileSelected() {
        return true;
    }
}
