package nlu.fit.leanhduc.view.component.fileChooser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.util.Constraint;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileChooser extends JPanel {
    JFileChooser fileChooser;
    JLabel label;
    JButton button;
    FileChooserEvent event;
    boolean onlyBtn;
    String textBtn = "Chọn file";
    Border combinedBorder;
    String path;

    public FileChooser(Border borderFactory) {
        this.combinedBorder = borderFactory;
        this.onlyBtn = false;
        init();
    }

    public FileChooser() {
        this.onlyBtn = false;
        initWithLabel();
    }

    public FileChooser(String textBtn) {
        this.textBtn = textBtn;
        init();
    }

    public FileChooser(boolean onlyBtn, String textBtn) {
        this.onlyBtn = true;
        this.textBtn = textBtn;
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout(5, 5));
        createButton();
        if (!onlyBtn) {
            createLabel();
            this.add(label, BorderLayout.CENTER);
        }
        if (onlyBtn)
            this.add(button, BorderLayout.CENTER);
        else
            this.add(button, BorderLayout.EAST);

        this.setBorder(combinedBorder);
    }

    private void initWithLabel() {
        this.setLayout(new BorderLayout(5, 0));
        createButton();
        createLabel();
        this.add(label, BorderLayout.CENTER);
        this.add(button, BorderLayout.EAST);
        this.setBorder(combinedBorder);
        setEvent();
    }

    private void createLabel() {
        label = new JLabel();
        label.setFont(Constraint.FONT_MEDIUM);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    private void createButton() {
        button = new JButton(textBtn);
        button.addActionListener(e -> click());
    }

    private void setEvent() {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        click();
                    }
                });
    }

    private void click() {
        if (!this.event.onBeforeFileSelected()) {
            return;
        }
        fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        switch (option) {
            case JFileChooser.APPROVE_OPTION:
                File selectedFile = fileChooser.getSelectedFile();
                String name = selectedFile.getName();
                if (label != null)
                    label.setText(name);
                setPath(selectedFile.getAbsolutePath());
                this.event.onFileSelected(selectedFile);
                break;
            case JFileChooser.CANCEL_OPTION:
                this.event.onFileUnselected();
                break;
            case JFileChooser.ERROR_OPTION:
                this.event.onError("Error");
                break;
        }
    }

    public void cancel() {
        label.setText("");
    }

    public void setIcon(ImageIcon icon) {
        button.setIcon(icon);
        button.setIconTextGap(10);
    }
}
