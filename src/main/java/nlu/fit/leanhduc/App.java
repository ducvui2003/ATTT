package nlu.fit.leanhduc;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import nlu.fit.leanhduc.controller.MainController;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        new MainController();

    }
}
