package nlu.fit.leanhduc.view.component.dialog;


import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.util.Cipher;
import nlu.fit.leanhduc.util.Language;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Objects;

public class GenerateKeyDialog extends CustomDialog {
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
            System.out.println("You selected: " + selectedItem);
        });
        panelGenerateKey.add(comboBoxCipher);
        JComboBox<Language> comboBoxLanguage = new JComboBox<>(Language.values());
        this.language = (Language) comboBoxLanguage.getSelectedItem();
        comboBoxLanguage.addActionListener(e -> {
            Language selectedItem = (Language) comboBoxLanguage.getSelectedItem();
            System.out.println("You selected: " + selectedItem);
        });
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
        ((JFormattedTextField) inputKeyLength).setValue(1);
        panelGenerateKey.add(new JLabel("Độ dài khóa:"));
        panelGenerateKey.add(inputKeyLength);
    }

    public void createFileOutput() {
        fileChooser = new FileChooser(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Lưu khóa vào file:")
        ));
        this.add(fileChooser);
    }

    private void createButtonSubmit() {
        Button button = new Button("Tạo khóa");
        panelGenerateKey.add(button);
        button.addActionListener(e -> {
            key = Objects.requireNonNull(KeyGeneratorFactory.getKeyGenerator(cipher, language)).generateKey();
            textArea.setText(key.toString());
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
}
