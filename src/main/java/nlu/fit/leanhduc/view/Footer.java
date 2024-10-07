package nlu.fit.leanhduc.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Footer extends JPanel {
    JProgressBar progressBar;
    JLabel statusLabel;

    public Footer() {
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.progressBar = new JProgressBar();
        this.statusLabel = new JLabel("Loading...");
        this.add(progressBar);
        this.add(statusLabel);
    }


    public void startLoading() {
        // Show the dialog
        this.progressBar.setVisible(true);
        this.statusLabel.setVisible(true);
        this.progressBar.setIndeterminate(true);
    }

    public void stopLoading() {
        // Hide the dialog
        this.progressBar.setVisible(false);
        this.statusLabel.setVisible(false);
        this.progressBar.setIndeterminate(false);
    }
}
