package nlu.fit.leanhduc.component.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoadingDialog extends CustomDialog {
    JProgressBar progressBar;

    public LoadingDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        init();
        this.setLocationRelativeTo(owner);
        this.handleClose();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setSize(300, 100);

        // Create a progress bar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);


        JLabel loadingLabel = new JLabel("Please wait...");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(loadingLabel, BorderLayout.NORTH);
        this.add(progressBar, BorderLayout.CENTER);
    }

    public void startLoading() {
        // Show the dialog
        this.setVisible(true);
    }

    public void stopLoading() {
        // Hide the dialog
        this.setVisible(false);
        this.dispose();
    }

    public void performTask() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate a background task that takes time
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(500); // Simulate task
                }
                return null;
            }

            @Override
            protected void done() {
                // Stop the loading dialog when the task is done
                stopLoading();
            }
        };
        worker.execute();
        startLoading();
    }
}
