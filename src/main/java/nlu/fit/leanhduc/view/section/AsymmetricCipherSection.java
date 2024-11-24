package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.AsymmetricCipherController;
import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandlerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AsymmetricCipherSection extends JPanel implements ActionListener, PanelTextHandlerEvent, FileChooserEvent {
    MainController controller;
    List<CipherSpecification> cipherSpecifications = List.of(CipherSpecification.findCipherSpecification(Cipher.RSA));
    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Size> comboBoxKeySize;
    Padding currentPadding;
    Size currentKeySize;
    JButton btnGenerateKey;
    FileChooserButton fileChooser;
    JLabel keyStatus;
    JPanel container;
    JRadioButton encryptByPublicKey, encryptByPrivateKey;
    ButtonGroup group;
    JTabbedPane tabbedPane;
    JTextArea publicKeyField, privateKeyField;
    PanelTextHandler panelTextHandler;
    JPanel detailKey;

    public AsymmetricCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);
        JPanel panelGenerateKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

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

        // Initialize Key Size ComboBox and Listener
        this.comboBoxKeySize = new JComboBox<>();


        // Add Components to Panel
        panelGenerateKey.add(new JLabel("Cipher:"));
        panelGenerateKey.add(this.comboBoxCipher);
        panelGenerateKey.add(new JLabel("Mode:"));
        panelGenerateKey.add(this.comboBoxMode);
        panelGenerateKey.add(new JLabel("Padding:"));
        panelGenerateKey.add(this.comboBoxPadding);
        panelGenerateKey.add(new JLabel("Key Size:"));
        panelGenerateKey.add(this.comboBoxKeySize);


        this.btnGenerateKey = new JButton("Tạo khóa");
        this.btnGenerateKey.addActionListener(this);
        panelGenerateKey.add(this.btnGenerateKey);

        this.fileChooser = new FileChooserButton("Lưu khóa", null);
        this.fileChooser.setEvent(this);
        panelGenerateKey.add(this.fileChooser);

        this.encryptByPrivateKey = new JRadioButton("Ma hoá bằng khóa bí mật");
        this.encryptByPrivateKey.setSelected(true);
        this.encryptByPublicKey = new JRadioButton("Ma hoá bằng khóa công khai");

        group = new ButtonGroup();
        group.add(encryptByPrivateKey);
        group.add(encryptByPublicKey);


        this.publicKeyField = SwingComponentUtil.createTextArea();
        this.privateKeyField = SwingComponentUtil.createTextArea();

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1, 0)
                        .insets(10)
                        .gridSpan(6, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                panelGenerateKey
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)
                        .weight(1, 0)
                        .insets(10)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Chọn khóa để mã hoá")
        );

        JPanel panelOptionEncrypt = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panelOptionEncrypt.add(encryptByPrivateKey);
        panelOptionEncrypt.add(encryptByPublicKey);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 1)
                        .insets(10)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                panelOptionEncrypt
        );


        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .insets(10)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Tình trạng khóa")
        );

        this.keyStatus = new JLabel();

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)
                        .insets(10)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                keyStatus
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 3)
                        .insets(10)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Chi tiết khóa:")
        );

        this.detailKey = new JPanel(new GridBagLayout());

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 3)
                        .insets(10)
                        .weight(1.0, 0.0)
                        .gridSpan(5, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                detailKey
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                new JLabel("Khóa cong khai")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .gridSpan(4, 2)
                        .weight(0.8, 1.0)
                        .insets(10)
                        .fill(GridBagConstraints.BOTH)
                        .build(),
                new JScrollPane(publicKeyField)
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 6)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10)
                        .build(),
                new JLabel("Khóa bí mật")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 6)
                        .gridSpan(4, 2)
                        .weight(0.8, 1.0)
                        .insets(10)
                        .fill(GridBagConstraints.BOTH)
                        .build(),
                new JScrollPane(privateKeyField)
        );
        updateModeAndPadding();
        createTabbedPane();
        setKeyStatus(false);
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
    }

    // Update Padding and IV Size when Mode changes
    private void updatePaddingAndIvSize() {
        Cipher selectedCipher = (Cipher) this.comboBoxCipher.getSelectedItem();
        CipherSpecification selectedSpec = getCipherSpecification(selectedCipher);


        this.comboBoxPadding.removeAllItems();
        if (this.getSelectedMode() != null) {
            selectedSpec.getValidModePaddingCombinations().get(this.getSelectedMode())
                    .forEach(padding -> this.comboBoxPadding.addItem(padding));
        }
        this.currentPadding = (Padding) this.comboBoxPadding.getSelectedItem();


    }

    // Update Key Size based on selected Cipher
    private void updateKeySize() {
        CipherSpecification selectedSpec = getCipherSpecification(this.getSelectedCipher());
        this.comboBoxKeySize.removeAllItems();
        selectedSpec.getSupportedKeySizes().forEach(size -> this.comboBoxKeySize.addItem(size));
        this.currentKeySize = (Size) this.comboBoxKeySize.getSelectedItem();
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
                        .grid(0, 8)        // Starting at the first column in the desired row
                        .gridSpan(10, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                this.tabbedPane);
    }


    private CipherSpecification getCipherSpecification(Cipher cipher) {
        switch (cipher) {
            case RSA:
                return CipherSpecification.findCipherSpecification(Cipher.RSA);
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

    private boolean getCurrentRadio() {
        if (encryptByPublicKey.isSelected()) {
            return true;
        }
        return false;
    }

    private void updateKey(String publicKey, String privateKey) {
        this.publicKeyField.setText(publicKey);
        this.privateKeyField.setText(privateKey);
    }

    private void setKeyStatus(boolean isSuccess) {
        this.keyStatus.setIcon(isSuccess ? MetadataConfig.getINSTANCE().getSuccessIcon() : MetadataConfig.getINSTANCE().getWarningIcon());
        this.keyStatus.setText(isSuccess ? "Đã xét khóa" : "Chưa xét khóa");
        if (!isSuccess) {
            this.keyStatus.setToolTipText("Vui lòng xét khóa trước khi mã hóa hoặc giải mã");
            this.detailKey.setVisible(false);
            this.updateKey("", "");
        } else {
            this.detailKey.setVisible(true);
            createDetailKey();
        }
    }

    private void createDetailKey() {
        this.detailKey.removeAll();
        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Thuật toán: " + this.getSelectedCipher().getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1.0, 1)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Mode: " + this.getSelectedMode().getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(2, 0)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Padding: " + this.getSelectedPadding().getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(3, 0)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Key Size: " + this.getSelectedKeySize() + " bits")
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnGenerateKey) {
            try {
                Map<String, String> key = AsymmetricCipherController.getInstance().generateKey(
                        this.getSelectedCipher(),
                        this.getSelectedMode(),
                        this.getSelectedPadding(),
                        this.getSelectedKeySize()
                );
                updateKey(key.get("public-key"), key.get("private-key"));
                setKeyStatus(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public String onEncrypt(String plainText) {
        String result = "";
        try {
            result = AsymmetricCipherController.getInstance().encrypt(
                    this.publicKeyField.getText(),
                    this.privateKeyField.getText(),
                    plainText,
                    this.getSelectedCipher(),
                    this.getSelectedMode(),
                    this.getSelectedPadding(),
                    this.getSelectedKeySize(),
                    this.getCurrentRadio());
            return result;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public String onDecrypt(String cipherText) {
        String result = "";
        try {
            result = AsymmetricCipherController.getInstance().decrypt(
                    this.publicKeyField.getText(),
                    this.privateKeyField.getText(),
                    cipherText,
                    this.getSelectedCipher(),
                    this.getSelectedMode(),
                    this.getSelectedPadding(),
                    this.getSelectedKeySize(),
                    this.getCurrentRadio());
            return result;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
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
