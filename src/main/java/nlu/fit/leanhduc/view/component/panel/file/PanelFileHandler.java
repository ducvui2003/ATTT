package nlu.fit.leanhduc.view.component.panel.file;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.view.component.GridBagConstraintsBuilder;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserLabel;
import nlu.fit.leanhduc.view.component.panel.text.PanelHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelFileHandler extends PanelHandler implements ActionListener {
    JPanel container;
    PanelFileHandlerEncrypt panelFileHandlerEncrypt;

    public PanelFileHandler() {
    }

    @Override
    protected void init() {
        this.setLayout(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.add(container, BorderLayout.NORTH);
        this.panelFileHandlerEncrypt = new PanelFileHandlerEncrypt();

        createPanelEncrypt();
    }

    private void createPanelEncrypt() {
        JPanel panel = new JPanel(new GridBagLayout());
        Border combinedBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Mã hóa file"));
        panel.setBorder(combinedBorder);
        panel.add(panelFileHandlerEncrypt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        Object source = e.getSource();
//        if (source == btnEncrypt) {
//            String src = this.fileEncrypt.getPath();
//            String dest = this.saveFileEncrypt.getPath();
//            System.out.println("Encrypt: " + src + " -> " + dest);
//        }
    }
}
