package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.DigitalSignatureController;
import nlu.fit.leanhduc.service.cipher.CipherSpecification;
import nlu.fit.leanhduc.service.digital.DigitalSignatureSpecification;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Hash;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandler;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextHandlerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DigitalSignatureSection extends JPanel implements PanelTextHandlerEvent, ActionListener {
    JTabbedPane tabbedPane;
    JPanel container;
    JComboBox<String> cbType;
    JComboBox<Hash> cbHash;
    JComboBox<Size> cbKeySize;
    JButton btnCreateKey;
    JTextArea txtPublicKey, txtPrivateKey;
    PanelTextHandler panelTextHandler;
    PanelFileHandler panelFileHandler;
    DigitalSignatureSpecification specification = DigitalSignatureSpecification.findDigitalSignatureSpecification(Cipher.DSA);

    public DigitalSignatureSection() {
        createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(this.container, BorderLayout.NORTH);
        this.createComboBoxType();
        this.createPanelKey();
    }

    private void createComboBoxType() {
        this.cbType = new JComboBox<>(new String[]{"Ký", "Xác thực"});
        this.cbType.setSelectedIndex(0);
        this.cbHash = new JComboBox<>(this.specification.getHashFunctions().toArray(new Hash[0]));
        this.cbHash.setSelectedIndex(0);
        this.cbKeySize = new JComboBox<>(this.specification.getKeySize().toArray(new Size[0]));
        this.cbKeySize.setSelectedIndex(0);
    }

    private void createPanelKey() {

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 0)
                        .weight(0.25, 0) // Ensure weights are balanced
                        .insets(5, 5, 5, 5)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Sử dụng khóa để: ")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 0)
                        .weight(1, 0) // Same weight as above
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                this.cbType
        );

        JPanel panelCreateKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelCreateKey.add(new JLabel("Thuật toán hash: "));
        panelCreateKey.add(this.cbHash);
        panelCreateKey.add(new JLabel("Kích thước khóa: "));
        panelCreateKey.add(this.cbKeySize);
        this.btnCreateKey = new JButton("Tạo khóa");
        this.btnCreateKey.addActionListener(this);
        panelCreateKey.add(btnCreateKey);


        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 2)
                        .weight(1, 1)
                        .gridSpan(7, 1)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                panelCreateKey
        );


        this.txtPublicKey = SwingComponentUtil.createTextArea();
        this.txtPrivateKey = SwingComponentUtil.createTextArea();

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 4)
                        .weight(0.25, 0) // Ensure weights are balanced
                        .insets(5, 5, 5, 5)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Khóa công khai: ")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 4)
                        .weight(1, 1)
                        .gridSpan(6, 3)
                        .fill(GridBagConstraints.BOTH)
                        .build(),
                new JScrollPane(this.txtPublicKey)
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 8)
                        .weight(0.25, 0) // Ensure weights are balanced
                        .insets(5, 5, 5, 5)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .build(),
                new JLabel("Khóa bí mật: ")
        );

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(1, 8)
                        .weight(1, 1)
                        .gridSpan(6, 3)
                        .fill(GridBagConstraints.BOTH)
                        .build(),
                new JScrollPane(this.txtPrivateKey)
        );
        createTabbedPane();
    }

    public void createTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        this.panelTextHandler = new PanelTextHandler(this);
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa Chuỗi", panelTextHandler);
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));

        SwingComponentUtil.addComponentGridBag(
                this.container,
                GridBagConstraintsBuilder.builder()
                        .grid(0, 11)        // Starting at the first column in the desired row
                        .gridSpan(10, 1)
                        .weight(1, 0)
                        .fill(GridBagConstraints.HORIZONTAL)
                        .insets(10, 0, 10, 0) // Optional padding around the separator
                        .build(),
                this.tabbedPane);
    }

    private Hash getSelectedHash() {
        return (Hash) this.cbHash.getSelectedItem();
    }

    private Size getKeySize() {
        return (Size) this.cbKeySize.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.btnCreateKey) {

            try {
                Map<String, String> result = DigitalSignatureController.getInstance().generateKey(
                        getSelectedHash(),
                        getKeySize()
                );
                this.txtPublicKey.setText(result.get("public-key"));
                this.txtPrivateKey.setText(result.get("private-key"));
            } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                JOptionPane.showMessageDialog(this, "Không thể tạo khóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public String onEncrypt(String plainText) {
        return "";
    }

    @Override
    public String onDecrypt(String cipherText) {
        return "";
    }
}
