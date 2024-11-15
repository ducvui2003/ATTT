package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.service.cipher.symmetric.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConverter;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserButton;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandlerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SymmetricCipherSection extends JPanel implements PanelTextHandlerEvent, ActionListener {
    MainController controller;
    List<CipherSpecification> cipherSpecifications =
            List.of(
                    CipherSpecification.findCipherSpecification(Cipher.AES),
                    CipherSpecification.findCipherSpecification(Cipher.DES),
                    CipherSpecification.findCipherSpecification(Cipher.DESEDE)
            );

    JComboBox<Cipher> comboBoxCipher;
    JComboBox<Mode> comboBoxMode;
    JComboBox<Padding> comboBoxPadding;
    JComboBox<Size> comboBoxKeySize;
    JComboBox<Size> comboBoxIVSize;
    JButton btnGenerateKey;
    FileChooserButton btnLoadKey;

    GridBagConstraints gbc;
    JPanel container;
    JTabbedPane tabbedPane;
    PanelHandler panelTextHandler, panelFileHandler;
    SymmetricCipherNative symmetricCipherNative;
    JLabel keyStatus;
    JPanel detailKey;
    JTextArea showSecretKey, showIv;

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

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setEnabled(false);
        textArea.setRows(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private void createGeneratePanel() {
        // Initialize Cipher ComboBox and Listener
        createComboBox();
        this.btnGenerateKey = new JButton("Tạo khóa");
        this.btnGenerateKey.addActionListener(this);
        this.btnLoadKey = new FileChooserButton("Tải khóa", MetadataConfig.INSTANCE.getUploadIcon());
        this.showSecretKey = createTextArea();
        this.showIv = createTextArea();
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
                        .gridSpan(5, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.keyStatus
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(10, 2)
                        .weight(1.0, 0.0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                btnLoadKey
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
                        .gridSpan(15, 2)
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
                        .gridSpan(15, 2)
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

        // Initialize Mode ComboBox and Listener
        this.comboBoxMode = new JComboBox<>();
        this.comboBoxMode.addActionListener(e -> updatePaddingAndIvSize());

        // Initialize Padding ComboBox and Listener
        this.comboBoxPadding = new JComboBox<>();
        this.comboBoxPadding.addActionListener(e -> updateIvSize());

        // Initialize Key Size ComboBox and Listener
        this.comboBoxKeySize = new JComboBox<>();

        // Initialize IV Size ComboBox (No listener needed)
        this.comboBoxIVSize = new JComboBox<>();
    }

    // Update Mode and Padding when Cipher changes
    private void updateModeAndPadding() {
        Cipher selectedCipher = this.getSelectedCipher();
        CipherSpecification selectedSpec = CipherSpecification.findCipherSpecification(selectedCipher);

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
        Cipher selectedCipher = getSelectedCipher();
        CipherSpecification selectedSpec = CipherSpecification.findCipherSpecification(selectedCipher);

        // Temporarily remove padding listener to avoid triggering it during updates
        ActionListener paddingListener = this.comboBoxPadding.getActionListeners()[0];
        this.comboBoxPadding.removeActionListener(paddingListener);

        // Update Padding ComboBox
        this.comboBoxPadding.removeAllItems();
        if (this.getSelectedMode() != null) {
            selectedSpec.getValidModePaddingCombinations().get(this.getSelectedMode())
                    .forEach(padding -> this.comboBoxPadding.addItem(padding));
        }

        // Re-add the padding listener
        this.comboBoxPadding.addActionListener(paddingListener);

        // Update IV Size based on Mode
        updateIvSize();
    }

    // Update Key Size based on selected Cipher
    private void updateKeySize() {
        CipherSpecification selectedSpec = CipherSpecification.findCipherSpecification(this.getSelectedCipher());
        this.comboBoxKeySize.removeAllItems();
        selectedSpec.getSupportedKeySizes().forEach(size -> this.comboBoxKeySize.addItem(size));
    }

    // Update IV Size based on selected Mode
    private void updateIvSize() {
        CipherSpecification selectedSpec = CipherSpecification.findCipherSpecification(this.getSelectedCipher());
        Mode selectedMode = (Mode) this.comboBoxMode.getSelectedItem();

        this.comboBoxIVSize.removeAllItems();
        if (this.getSelectedIVSize() != null) {
            this.comboBoxIVSize.addItem(this.getSelectedIVSize());
        } else {
            this.comboBoxIVSize.addItem(Size.Size_0);
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
        if (e.getSource() == btnGenerateKey) {
            try {
                SymmetricCipherNative cipher = SymmetricCipherNativeController.getInstance().getAlgorithm(
                        this.getSelectedCipher(),
                        this.getSelectedMode(),
                        this.getSelectedPadding(),
                        this.getSelectedKeySize(),
                        this.getSelectedIVSize()
                );
                Map<String, String> key = SymmetricCipherNativeController.getInstance().generateKey(cipher);
                this.showSecretKey.setText(key.get("secret-key"));
                this.showIv.setText(key.get("iv"));
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
            return symmetricCipherNative.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String onDecrypt(String cipherText) {
        try {
            return symmetricCipherNative.decrypt(cipherText);
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
                new JLabel("IV Size: " + this.getSelectedIVSize() + " bits")
        );
    }
}