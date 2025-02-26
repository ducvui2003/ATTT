package nlu.fit.leanhduc.view;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.view.component.menu.Menu;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.view.section.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainView extends JFrame {
    Menu menu;
    JTabbedPane tabbedPane;
    Map<String, JPanel> panelMap;
    MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;
    }

    public void createUIComponents() {
        createMenu();
        createTabPanel();
        setMetadata();
    }


    private void createMenu() {
        menu = new Menu(this, this.controller);
        this.setJMenuBar(menu);
    }

    private void createTabPanel() {
        tabbedPane = new JTabbedPane();
        panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa thay thế", new SubstitutionCipherSection(controller));
        panelMap.put("Mã hóa đối xứng", new SymmetricCipherSection(controller));
        panelMap.put("Mã hóa bất đối xứng", new AsymmetricCipherSection(controller));
        panelMap.put("Hàm Băm", new HashFunctionSection());
        panelMap.put("Chữ ký điện tử", new DigitalSignatureSection());

        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));
        this.add(tabbedPane);
    }

    private void setMetadata() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        this.setLocation(0, 0);
        this.setName(Constraint.APP_NAME);
        this.setVisible(true);
        this.setIconImage(Constraint.APP_ICON_IMAGE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
