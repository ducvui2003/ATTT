package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AsymmetricCipherSection extends JPanel implements ActionListener {
    MainController controller;
    List<CipherSpecification> cipherSpecifications = List.of(CipherSpecification.findCipherSpecification(Cipher.RSA), CipherSpecification.findCipherSpecification(Cipher.DES));
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Size> comboBoxKeySize;
    JComboBox<Size> comboBoxIVSize;
    Padding currentPadding;
    Size currentKeySize;
    JButton btnGenerateKey;
    JLabel keyStatus;

    public AsymmetricCipherSection(MainController controller) {
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
    }


    // Update Mode and Padding when Cipher changes
    private void updateModeAndPadding() {
        Cipher selectedCipher = this.getSelectedCipher();
        CipherSpecification selectedSpec = getCipherSpecification(selectedCipher);

        // Temporarily remove mode listener to avoid triggering it during updates
        ActionListener modeListener = this.comboBoxMode.getActionListeners()[0];
        this.comboBoxMode.removeActionListener(modeListener);

        // Update Mode ComboBox
        this.comboBoxMode.removeAllItems();
        selectedSpec.getValidModePaddingCombinations().keySet().forEach(mode -> this.comboBoxMode.addItem(mode));

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

        // Temporarily remove padding listener to avoid triggering it during updates
        ActionListener paddingListener = this.comboBoxPadding.getActionListeners()[0];
        this.comboBoxPadding.removeActionListener(paddingListener);

        // Update Padding ComboBox
        this.comboBoxPadding.removeAllItems();
        if (this.getSelectedMode() != null) {
            selectedSpec.getValidModePaddingCombinations().get(this.getSelectedMode())
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
        CipherSpecification selectedSpec = getCipherSpecification(this.getSelectedCipher());
        this.comboBoxKeySize.removeAllItems();
        selectedSpec.getSupportedKeySizes().forEach(size -> this.comboBoxKeySize.addItem(size));
        this.currentKeySize = (Size) this.comboBoxKeySize.getSelectedItem();
    }

    // Update IV Size based on selected Mode
    private void updateIvSize() {
        CipherSpecification selectedSpec = getCipherSpecification(this.getSelectedCipher());
        Mode selectedMode = (Mode) this.comboBoxMode.getSelectedItem();

        this.comboBoxIVSize.removeAllItems();
        if (this.getSelectedIVSize() != null) {
            this.comboBoxIVSize.addItem(this.getSelectedIVSize());
        }
    }

    private void handleKeySizeChange() {
        Size selectedKeySize = this.getSelectedKeySize();
        System.out.println("Selected Key Size: " + selectedKeySize);
    }


    private CipherSpecification getCipherSpecification(Cipher cipher) {
        switch (cipher) {
            case RSA:
                return CipherSpecification.findCipherSpecification(Cipher.RSA);
            case DES:
                return CipherSpecification.findCipherSpecification(Cipher.DES);
            default:
                throw new IllegalArgumentException("Unknown cipher: " + cipher);
        }
    }

    private Cipher getSelectedCipher() {
        return (Cipher) this.comboBoxCipher.getSelectedItem();
    }

    private Mode getSelectedMode() {
        return (Mode) this.comboBoxMode.getSelectedItem();
    }

    private Padding getSelectedPadding() {
        return (Padding) this.comboBoxPadding.getSelectedItem();
    }

    private Size getSelectedKeySize() {
        return (Size) this.comboBoxKeySize.getSelectedItem();
    }

    private Size getSelectedIVSize() {
        return (Size) this.comboBoxIVSize.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnGenerateKey) {
//            try {
//                SymmetricCipherNative symmetricCipherNative = SymmetricCipherNativeController.getInstance().getAlgorithm(
//                        this.currentCipher.getName(),
//                        this.currentMode.getName(),
//                        this.currentPadding.getName(),
//                        this.currentKeySize,
//                        this.currentIVSize
//                );
//                symmetricCipherNative.loadKey(symmetricCipherNative.generateKey());
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
    }
}
