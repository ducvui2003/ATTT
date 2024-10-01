package view;

import component.menu.Menu;
import util.Constraint;
import view.section.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainView extends JFrame {
    JPanel panel;
    Menu menu;
    InputSection inputSection;
    JTabbedPane tabbedPane;

    Map<String, JPanel> panelMap;

    private void createUIComponents() {
        createMenu();

        this.setLayout(new BorderLayout(5, 5));

        inputSection = new InputSection();
        this.add(inputSection, BorderLayout.NORTH);
        createTabPanel();
        this.add(tabbedPane, BorderLayout.CENTER);
        setMetadata();
    }

    private void createMenu() {
        menu = new Menu(this);
        this.setJMenuBar(menu);
    }

    private void createTabPanel() {
        tabbedPane = new JTabbedPane();
        panelMap = Map.of(
                "Thuật toán cơ bản", new BasicAlthorismSection(),
                "Mã hóa đối xứng", new SymmetricEncryptionSection(),
                "Mã hóa bất đối xứng", new AsymmetricEncryptionSection(),
                "Chữ ký điện tử", new ElectronicSignature()
        );
        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));
    }

    private void setMetadata() {
        this.setName(Constraint.APP_NAME);
        this.setSize(Constraint.WIDTH, Constraint.HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(Constraint.APP_ICON_IMAGE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
