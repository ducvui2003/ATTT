package nlu.fit.leanhduc.view.component.dialog;

import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class LoadKeyDialog extends CustomDialog implements FileChooserEvent {
    FileChooser fileChooser;
    List<Cipher> cipherSupport2Language;
    JComboBox<Cipher> comboBoxCipher;
    Cipher cipher;
    GridBagConstraints gbc;

    public LoadKeyDialog(Frame frameContainer) {
        super(frameContainer, "Chọn khóa", Dialog.ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void init() {
        this.cipherSupport2Language = List.of(Cipher.SHIFT, Cipher.SUBSTITUTION, Cipher.AFFINE, Cipher.HILL, Cipher.VIGENERE);
        this.comboBoxCipher = new JComboBox<>(Cipher.values());
        this.setLayout(new GridBagLayout());
        this.fileChooser = new FileChooser();
        this.fileChooser.setEvent(this);
        createPanelTypeKey();
        this.setSize(600, 400);
    }

    private void createPanelTypeKey() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expand components horizontally
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tải khóa từ file"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(fileChooser);

        // Header row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Merge first two cells in header
        panel.add(new JLabel("Nhập khóa"), gbc);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        createComboBox();
        panel.add(comboBoxCipher, gbc);

        this.add(panel);
    }

    private void createComboBox() {
        this.comboBoxCipher.addActionListener(e -> {
            Cipher selectedItem = (Cipher) comboBoxCipher.getSelectedItem();
            cipher = selectedItem;
            System.out.println("You selected: " + selectedItem);
        });
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
}
