package nlu.fit.leanhduc;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import nlu.fit.leanhduc.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("Button.arc", 10);

            UIManager.put("Button.margin", new Insets(10, 20, 10, 20));

            UIManager.put("Component.arc", 10);

            UIManager.put("ProgressBar.arc", 10);

            UIManager.put("TextComponent.arc", 0);


            UIManager.put("TextComponent.arc", 10);

            UIManager.put("TextField.margin", new Insets(8, 8, 8, 8));

            UIManager.put("ComboBox.padding", new Insets(8, 8, 8, 8));

            UIManager.put("TabbedPane.selectedBackground", Color.white);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            new MainController();
        });


    }
}
