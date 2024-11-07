package nlu.fit.leanhduc.view.component.dialog;


import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.service.cipher.symmetric.vigenere.VigenereCipher;
import nlu.fit.leanhduc.service.key.IKeyDisplay;
import nlu.fit.leanhduc.util.Cipher;
import nlu.fit.leanhduc.util.FileUtil;
import nlu.fit.leanhduc.util.Language;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Objects;

public class GenerateKeyDialog extends CustomDialog implements FileChooserEvent {
    JTextField inputKeyLength;
    TextArea textArea;
    FileChooser fileChooser;
    Language language;
    Cipher cipher;
    Object key;
    private JPanel panelGenerateKey;

    public GenerateKeyDialog(Frame owner) {
        super(owner, "Tạo khóa mới", Dialog.ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        createPanelGenerateKey();
        createFileOutput();
        createTextArea();
        createResetBtn();
        this.setSize(600, 400);
    }

    private void createPanelGenerateKey() {
        panelGenerateKey = new JPanel();
        panelGenerateKey.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.add(panelGenerateKey);
        Border combinedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Thông tin khóa")
        );
        panelGenerateKey.setBorder(combinedBorder);
        createComboBox();
        createKeyLengthInput();
        createButtonSubmit();
    }

    private void createComboBox() {
        JComboBox<Cipher> comboBoxCipher = new JComboBox<>(Cipher.values());
        // Add an action listener to handle selection changes
        this.cipher = (Cipher) comboBoxCipher.getSelectedItem();
        comboBoxCipher.addActionListener(e -> {
            Cipher selectedItem = (Cipher) comboBoxCipher.getSelectedItem();
            cipher = selectedItem;
            System.out.println("You selected: " + selectedItem);
            inputKeyLength.setEnabled(selectedItem == Cipher.VIGENERE);
        });
        panelGenerateKey.add(comboBoxCipher);
        JComboBox<Language> comboBoxLanguage = new JComboBox<>(Language.values());
        this.language = (Language) comboBoxLanguage.getSelectedItem();
        comboBoxLanguage.addActionListener(e -> {
            Language selectedItem = (Language) comboBoxLanguage.getSelectedItem();
            language = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
        comboBoxLanguage.setToolTipText("Chọn ngôn ngữ để tạo khóa");
        panelGenerateKey.add(comboBoxLanguage);
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
        fileChooser = new FileChooser(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Lưu khóa vào file:")
        ));
        fileChooser.setEvent(this);
        this.add(fileChooser);
    }

    private void createButtonSubmit() {
        Button button = new Button("Tạo khóa");
        panelGenerateKey.add(button);
        button.addActionListener(e -> {
            key = Objects.requireNonNull(KeyGeneratorFactory.getKeyGenerator(cipher, language));
            if (key instanceof VigenereCipher vigenereCipher) {
                vigenereCipher.setKeyLength((Integer) ((JFormattedTextField) inputKeyLength).getValue());
                textArea.setText(vigenereCipher.generateKey().display());
                return;
            }

            if (((IKeyGenerator<?>) key).generateKey() instanceof IKeyDisplay keyDisplay) {
                textArea.setText(keyDisplay.display());
            }
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

    public void createResetBtn() {
        Button resetBtn = new Button("Reset");
        panelGenerateKey.add(resetBtn);
        resetBtn.addActionListener(e -> {
            textArea.setText("");
            key = null;
            fileChooser.setFileChooser(null);
        });
    }

    @Override
    public void onFileSelected(File file) {
        try {
            FileUtil.writeStringToFile(file.getAbsolutePath(), textArea.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể lưu file", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        if (textArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có khóa để lưu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
