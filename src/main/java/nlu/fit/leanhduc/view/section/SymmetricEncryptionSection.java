package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.util.Cipher;
import nlu.fit.leanhduc.view.component.dialog.CreateAffineKeyDialog;
import nlu.fit.leanhduc.view.component.dialog.CreateKeyDialog;
import nlu.fit.leanhduc.view.component.dialog.CreateShiftKeyDialog;
import nlu.fit.leanhduc.view.component.dialog.CreateSubstitutionDialog;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class SymmetricEncryptionSection extends JPanel implements FileChooserEvent, ActionListener {
    FileChooser fileChooser;
    List<Cipher> cipherSupport2Language;
    JComboBox<Cipher> comboBoxCipher;
    Cipher cipher;
    JButton btnInputKey;
    CreateKeyDialog createKeyDialog;

    public SymmetricEncryptionSection() {
        createUIComponents();
    }

    public void createUIComponents() {
        this.setLayout(new BorderLayout(5, 5));
        this.cipherSupport2Language = List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.HILL, Cipher.VIGENERE);
        this.comboBoxCipher = new JComboBox<>(Cipher.values());
        createKeyPanel();
    }

    private void createKeyPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 5));
        this.add(panel, BorderLayout.NORTH);
        panel.add(new JLabel("Chọn thuật toán"));
        createComboBox();
        panel.add(comboBoxCipher);

        this.fileChooser = new FileChooser();
        this.fileChooser.setEvent(this);
        panel.add(new JLabel("Tải khóa từ file"));
        panel.add(fileChooser);

        btnInputKey = new JButton("Nhập khóa");
        panel.add(btnInputKey);
        handleTypeKey();
    }

    private void createComboBox() {
        this.cipher = (Cipher) comboBoxCipher.getSelectedItem();
        this.comboBoxCipher.addActionListener(e -> {
            Cipher selectedItem = (Cipher) comboBoxCipher.getSelectedItem();
            cipher = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
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
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        switch (cipher) {
            case SHIFT:
                createKeyDialog = new CreateShiftKeyDialog((Frame) parentWindow);
                createKeyDialog.openDialog();
                break;
            case SUBSTITUTION:
                createKeyDialog = new CreateSubstitutionDialog((Frame) parentWindow);
                createKeyDialog.openDialog();
                break;
            case AFFINE:
                createKeyDialog = new CreateAffineKeyDialog((Frame) parentWindow);
                createKeyDialog.openDialog();
                break;
            case HILL:
                break;
            case VIGENERE:
                break;
        }
    }
}
