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
    Footer footer;
    MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;
    }

    public void createUIComponents() {
        createMenu();
        this.setLayout(new BorderLayout(5, 5));
        createTabPanel();
        createFooter();
        setMetadata();
    }


    private void createMenu() {
        menu = new Menu(this, this.controller);
        this.setJMenuBar(menu);
    }

    private void createTabPanel() {
        tabbedPane = new JTabbedPane();
        panelMap = new LinkedHashMap<>();
        panelMap.put("Mã hóa đối xứng", new SymmetricEncryptionSection());
        panelMap.put("Mã hóa bất đối xứng", new AsymmetricEncryptionSection());
        panelMap.put("Chữ ký điện tử", new ElectronicSignature());

        panelMap.forEach((k, v) -> tabbedPane.addTab(k, v));
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private void setMetadata() {
        this.setName(Constraint.APP_NAME);
        this.setSize(Constraint.WIDTH, Constraint.HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(Constraint.APP_ICON_IMAGE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createFooter() {
        footer = new Footer();
        this.add(footer, BorderLayout.SOUTH);
        footer.startLoading();
    }
}
