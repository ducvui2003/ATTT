package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.*;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;
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

public class SymmetricCipherSection extends JPanel implements PanelTextHandlerEvent, ActionListener {
    MainController controller;
    List<CipherSpecification> cipherSpecifications =
            List.of(
                    CipherSpecification.findCipherSpecification(Cipher.AES),
                    CipherSpecification.findCipherSpecification(Cipher.DES),
                    CipherSpecification.findCipherSpecification(Cipher.DESEDE),
                    CipherSpecification.findCipherSpecification(Cipher.RC2),
                    CipherSpecification.findCipherSpecification(Cipher.RC4),
                    CipherSpecification.findCipherSpecification(Cipher.BLOWFISH),
                    CipherSpecification.findCipherSpecification(Cipher.CAMELLIA),
                    CipherSpecification.findCipherSpecification(Cipher.IDEA)
            );

    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Size> comboBoxKeySize;
    JComboBox<Size> comboBoxIVSize;
    CipherSpecification selectedSpec;
    JButton btnGenerateKey;
    FileChooserLoadKey btnLoadKey;
    FileChooserSaveKey btnSaveKey;
    GridBagConstraints gbc;
    JPanel container;
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler, panelFileHandler;
    JLabel keyStatus;
    JPanel detailKey;
    JTextArea showSecretKey, showIv;
    String cipher;


    public SymmetricCipherSection(MainController controller) {
        this.controller = controller;
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.add(this.container, BorderLayout.NORTH);
        createGeneratePanel();
        createTabbedPane();
    }


    private void createGeneratePanel() {
        createComboBox();
        this.btnGenerateKey = new JButton("Tạo khóa");
        this.btnGenerateKey.addActionListener(this);
        this.btnSaveKey = new FileChooserSaveKey(this, "Lưu khóa", MetadataConfig.INSTANCE.getSaveIcon());
        this.btnLoadKey = new FileChooserLoadKey(this, "Tải khóa", MetadataConfig.INSTANCE.getUploadIcon());
        this.showSecretKey = SwingComponentUtil.createTextArea();
        this.showIv = SwingComponentUtil.createTextArea();
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(0, 10, 0, 0)
                        .build(),
                new JLabel("Cipher:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxCipher
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(2, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Mode:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(3, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxMode
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(4, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Padding:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(5, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxPadding
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(6, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Key Size:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(7, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxKeySize
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(8, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("IV Size:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(9, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxIVSize
        );

        this.keyStatus = new JLabel();
        keyStatus.setHorizontalTextPosition(SwingConstants.LEFT);

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .weight(1.0, 0.0)
                        .insets(0, 10, 0, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Tình trạng khóa:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(7, 2)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.btnGenerateKey
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(8, 2)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                btnSaveKey
        );
        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(9, 2)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                btnLoadKey
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)
                        .weight(1.0, 0.0)
                        .gridSpan(5, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.keyStatus
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 3)
                        .insets(0, 10, 0, 0)
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
                        .weight(1.0, 0.0)
                        .gridSpan(5, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                detailKey
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 2)
                        .weight(1.0, 0.0)
                        .gridSpan(4, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.keyStatus
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .insets(10, 10, 0, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Secret key:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .gridSpan(9, 2)
                        .weight(1.0, 1.0)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10, 0, 10, 0)
                        .build(),
                new JScrollPane(this.showSecretKey)
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 6)
                        .insets(10, 10, 0, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("IV:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 6)
                        .gridSpan(10, 2)
                        .weight(1.0, 1.0)
                        .fill(GridBagConstraints.BOTH)
                        .insets(10, 0, 10, 0)
                        .build(),
                new JScrollPane(this.showIv)
        );


        updateModeAndPadding();
        setKeyStatus(false);
    }

    private void createComboBox() {
        this.comboBoxCipher = new JComboBox<>(cipherSpecifications.stream()
                .map(CipherSpecification::getAlgorithm)
                .toArray(Cipher[]::new));
        this.comboBoxCipher.addActionListener(e -> updateModeAndPadding());

        this.comboBoxMode = new JComboBox<>();
        this.comboBoxMode.addActionListener(e -> updatePaddingAndIvSize());

        this.comboBoxPadding = new JComboBox<>();
        this.comboBoxPadding.addActionListener(e -> updateIvSize());

        this.comboBoxKeySize = new JComboBox<>();

        this.comboBoxIVSize = new JComboBox<>();
    }

    private void updateModeAndPadding() {
        Cipher selectedCipher = this.getSelectedCipher();

        this.selectedSpec = CipherSpecification.findCipherSpecification(selectedCipher);

        ActionListener modeListener = this.comboBoxMode.getActionListeners()[0];
        this.comboBoxMode.removeActionListener(modeListener);

        this.comboBoxMode.removeAllItems();
        selectedSpec.getValidModePaddingCombinations().keySet().forEach(mode -> this.comboBoxMode.addItem(mode));

        this.comboBoxMode.addActionListener(modeListener);

        updatePaddingAndIvSize();
        updateKeySize();
        updateIvSize();
    }

    private void updatePaddingAndIvSize() {

        ActionListener paddingListener = this.comboBoxPadding.getActionListeners()[0];
        this.comboBoxPadding.removeActionListener(paddingListener);

        this.comboBoxPadding.removeAllItems();
        if (this.getSelectedMode() != null) {
            selectedSpec.getValidModePaddingCombinations().get(this.getSelectedMode())
                    .forEach(padding -> this.comboBoxPadding.addItem(padding));
        }

        this.comboBoxPadding.addActionListener(paddingListener);

        updateIvSize();
    }

    private void updateKeySize() {
        this.comboBoxKeySize.removeAllItems();
        selectedSpec.getSupportedKeySizes().forEach(size -> this.comboBoxKeySize.addItem(size));
    }

    private void updateIvSize() {
        Mode selectedMode = (Mode) this.comboBoxMode.getSelectedItem();

        this.comboBoxIVSize.removeAllItems();

        Size size = selectedSpec.getIvSizes().get(selectedMode);
        if (size != null) {
            this.comboBoxIVSize.setEnabled(true);
            this.comboBoxIVSize.addItem(size);
        } else {
            this.comboBoxIVSize.setEnabled(false);
        }
    }

    private void createTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        this.panelTextHandler = new PanelTextHandler(this);
        this.panelFileHandler = new PanelFileHandler();
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa Chuỗi", panelTextHandler);
        panelMap.put("Mã hóa File", panelFileHandler);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 8)        // Starting at the first column in the desired row
                        .gridSpan(15, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                this.tabbedPane);
    }

    public Cipher getSelectedCipher() {
        return (Cipher) this.comboBoxCipher.getSelectedItem();
    }

    public Mode getSelectedMode() {
        return (Mode) this.comboBoxMode.getSelectedItem();
    }

    public Padding getSelectedPadding() {
        return (Padding) this.comboBoxPadding.getSelectedItem();
    }

    public Size getSelectedKeySize() {
        return (Size) this.comboBoxKeySize.getSelectedItem();
    }

    public Size getSelectedIVSize() {
        return (Size) this.comboBoxIVSize.getSelectedItem();
    }

    public String getBase64SecretKey() {
        return this.showSecretKey.getText();
    }

    public String getBase64Iv() {
        return this.showIv.getText();
    }

    public String getCipher() {
        return cipher;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerateKey) {
            try {
                SymmetricCipherNative cipher;
                Map<String, String> key = SymmetricCipherNativeController.getInstance().generateKey(
                        this.getSelectedCipher(),
                        this.getSelectedMode(),
                        this.getSelectedPadding(),
                        this.getSelectedKeySize(),
                        this.getSelectedIVSize()
                );
                this.showSecretKey.setText(key.get("secret-key"));
                this.showIv.setText(key.get("iv"));
                this.cipher = key.get("cipher");
                setKeyStatus(true);
            } catch (Exception ex) {
                setKeyStatus(false);
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public String onEncrypt(String plainText) {
        try {
            return SymmetricCipherNativeController.getInstance().encrypt(
                    getBase64SecretKey(),
                    getBase64Iv(),
                    plainText,
                    this.getSelectedCipher(),
                    this.getSelectedMode(),
                    this.getSelectedPadding(),
                    this.getSelectedKeySize(),
                    this.getSelectedIVSize()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String onDecrypt(String cipherText) {
        try {
            return SymmetricCipherNativeController.getInstance().decrypt(getBase64SecretKey(), getBase64Iv(),
                    cipherText,
                    this.getSelectedCipher(),
                    this.getSelectedMode(),
                    this.getSelectedPadding(),
                    this.getSelectedKeySize(),
                    this.getSelectedIVSize()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setKeyStatus(boolean isSuccess) {
        this.keyStatus.setIcon(isSuccess ? MetadataConfig.getINSTANCE().getSuccessIcon() : MetadataConfig.getINSTANCE().getWarningIcon());
        this.keyStatus.setText(isSuccess ? "Đã xét khóa" : "Chưa xét khóa");
        if (!isSuccess) {
            this.keyStatus.setToolTipText("Vui lòng xét khóa trước khi mã hóa hoặc giải mã");
            this.detailKey.setVisible(false);
            this.showSecretKey.setText("");
            this.showIv.setText("");
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

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(4, 0)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("IV Size: " + (this.getSelectedIVSize() == null ? 0 : this.getSelectedIVSize()).toString() + " bits")
        );
    }

    public void updateComboBox(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        this.selectedSpec = CipherSpecification.findCipherSpecification(cipher);
        this.comboBoxCipher.setSelectedItem(cipher);
        this.updateModeAndPadding();
        this.comboBoxMode.setSelectedItem(mode);
        this.comboBoxPadding.setSelectedItem(padding);
        this.updateKeySize();
        this.comboBoxKeySize.setSelectedItem(keySize);
        this.updateIvSize();
        this.comboBoxIVSize.setSelectedItem(ivSize);
    }

    public void updateKeyBase64(String base64SecretKey, String base64Iv) {
        this.showSecretKey.setText(base64SecretKey);
        this.showIv.setText(base64Iv);
        setKeyStatus(true);
    }
}