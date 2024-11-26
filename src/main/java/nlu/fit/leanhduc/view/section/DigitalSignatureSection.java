package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.DigitalSignatureController;
import nlu.fit.leanhduc.service.digital.DigitalSignatureSpecification;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Hash;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileSign;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileSignEvent;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileVerify;
import nlu.fit.leanhduc.view.component.panel.file.PanelFileVerifyEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextSign;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextSignEvent;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextVerify;
import nlu.fit.leanhduc.view.component.panel.text.PanelTextVerifyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DigitalSignatureSection extends JPanel implements ActionListener, PanelTextSignEvent, PanelTextVerifyEvent, PanelFileSignEvent, PanelFileVerifyEvent {
    JTabbedPane tabbedPane;
    JPanel container;
    JComboBox<String> cbMode;
    JComboBox<Hash> cbHash;
    JComboBox<Size> cbKeySize;
    JButton btnCreateKey;
    JTextArea txtPublicKey, txtPrivateKey;
    PanelTextSign panelTextSign;
    PanelTextVerify panelTextVerify;
    PanelFileSign panelFileSign;
    PanelFileVerify panelFileVerify;
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
        this.cbHash = new JComboBox<>(this.specification.getHashFunctions().toArray(new Hash[0]));
        this.cbHash.setSelectedIndex(0);
        this.cbKeySize = new JComboBox<>(this.specification.getKeySize().toArray(new Size[0]));
        this.cbKeySize.setSelectedIndex(0);
    }

    private void createPanelKey() {
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
        this.panelTextSign = new PanelTextSign(this);
        this.panelTextVerify = new PanelTextVerify(this);
        this.panelFileSign = new PanelFileSign(this);
        this.panelFileVerify = new PanelFileVerify(this);
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Ký văn bản", panelTextSign);
        panelMap.put("Xác thực văn bản", panelTextVerify);
        panelMap.put("Ký file", panelFileSign);
        panelMap.put("Xác thực file", panelFileVerify);
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
                JOptionPane.showMessageDialog(this, "Tạo khóa thành công", "Tạo khóa", JOptionPane.INFORMATION_MESSAGE);

            } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                JOptionPane.showMessageDialog(this, "Không thể tạo khóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private String getPublicKey() {
        return this.txtPublicKey.getText();
    }

    private String getPrivateKey() {
        return this.txtPrivateKey.getText();
    }


    @Override
    public String onSign(String plainText) {
        try {
            return DigitalSignatureController.getInstance().sign(plainText, getPrivateKey(), getSelectedHash(), getKeySize());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ký không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    @Override
    public void onVerify(String plainText, String signature) {
        try {
            boolean isSuccess = DigitalSignatureController.getInstance().verify(plainText, signature, getPublicKey(), getSelectedHash(), getKeySize());
            if (isSuccess)
                JOptionPane.showMessageDialog(this, "Xác thực thành công", "Xác thực", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xác thực không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xác thực không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String onSignFile(String src) {
        try {
            return DigitalSignatureController.getInstance().signFile(
                    src,
                    getPrivateKey(),
                    getSelectedHash(),
                    getKeySize());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ký không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    @Override
    public void onVerifyFile(String src, String signature) {
        try {
            boolean isSuccess = DigitalSignatureController.getInstance().verifyFile(
                    src,
                    signature,
                    getPublicKey(),
                    getSelectedHash(),
                    getKeySize());
            if (isSuccess)
                JOptionPane.showMessageDialog(this, "Chữ ký hợp lệ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Chữ ký không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ký không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
