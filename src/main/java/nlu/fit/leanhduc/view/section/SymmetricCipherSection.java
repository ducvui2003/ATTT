package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.service.cipher.symmetric.cryto.SymmetricCipherNative;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConverter;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.panel.PanelFileHandler;
import nlu.fit.leanhduc.view.component.panel.PanelHandler;
import nlu.fit.leanhduc.view.component.panel.PanelTextHandler;
import nlu.fit.leanhduc.view.component.panel.PanelTextHandlerEvent;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SymmetricCipherSection extends JPanel implements PanelTextHandlerEvent, ActionListener {
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

    GridBagConstraints gbc;
    JPanel container;
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler, panelFileHandler;
    SymmetricCipherNative symmetricCipherNative;
    ByteConverter byteConverter;
    JLabel keyStatus;
    JPanel detailKey;

    public SymmetricCipherSection(MainController controller) {
        this.controller = controller;
        this.byteConverter = new ByteConverter(new Base64ConversionStrategy());
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
        // Initialize Cipher ComboBox and Listener
        createComboBox();
        this.btnGenerateKey = new JButton("Tạo khóa");
        this.btnGenerateKey.addActionListener(this);
        // Add Components to Panel
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
                        .grid(7, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Key Size:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(8, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxKeySize
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(9, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("IV Size:")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(10, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.comboBoxIVSize
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(11, 0)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.btnGenerateKey
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
                        .gridSpan(15, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                detailKey
        );

        updateModeAndPadding();
        setKeyStatus(false);
    }

    private void createComboBox() {
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
                        .grid(0, 5)        // Starting at the first column in the desired row
                        .gridSpan(15, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                this.tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerateKey) {
            try {
                symmetricCipherNative = SymmetricCipherNativeController.getInstance().getAlgorithm(
                        this.currentCipher.getName(),
                        this.currentMode.getName(),
                        this.currentPadding.getName(),
                        this.currentKeySize,
                        this.currentIVSize
                );
                symmetricCipherNative.loadKey(symmetricCipherNative.generateKey());
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
            return this.byteConverter.convert(symmetricCipherNative.encrypt(plainText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String onDecrypt(String cipherText) {
        try {
            return symmetricCipherNative.decrypt(this.byteConverter.convert(cipherText));
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
                new JLabel("Thuật toán: " + this.currentCipher.getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 1)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Mode: " + this.currentMode.getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Padding: " + this.currentPadding.getDisplayName())
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 3)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Key Size: " + this.currentKeySize + " bits")
        );

        SwingComponentUtil.addComponentGridBag(
                this.detailKey,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .weight(1.0, 0.0)
                        .gridSpan(1, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("IV Size: " + this.currentIVSize + " bits")
        );
    }
}