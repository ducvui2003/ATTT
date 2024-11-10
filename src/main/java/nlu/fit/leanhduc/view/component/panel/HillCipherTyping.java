package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.HillKey;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HillCipherTyping extends KeyTypingPanel<HillKey> implements ActionListener {
    JPanel matrixView;
    JPanel matrixViewWrapper;

    public HillCipherTyping(MainController controller) {
        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(5));
        this.add(new JLabel("Khóa"));
        matrixViewWrapper = new JPanel(new BorderLayout(5, 5));
        matrixView = createMatrixView(2);
        this.add(matrixViewWrapper);
        matrixViewWrapper.add(matrixView, BorderLayout.CENTER);
        this.createChooseMatrix();
        this.setSize(400, 200);
    }

    @Override
    public HillKey getKey() {
        return null;
    }

    private JPanel createMatrixView(int size) {
        JPanel panel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                JFormattedTextField textField = SwingComponentUtil.createFormatTextFieldNumber(3, 1, null, null, false, true);
                panel.add(textField);
            }
        return panel;
    }

    private void createChooseMatrix() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Chọn ma trận"), BorderLayout.NORTH);
        JComboBox<Integer> comboBox = new JComboBox<>(new Integer[]{2, 3});
        comboBox.addActionListener(this);
        panel.add(comboBox, BorderLayout.CENTER);
        this.add(panel);
    }

    private void changeMatrixSize(int size) {
        this.matrixViewWrapper.removeAll();
        this.matrixView = createMatrixView(size);
        matrixViewWrapper.add(matrixView);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<Integer> comboBox = (JComboBox<Integer>) e.getSource();
        changeMatrixSize((int) comboBox.getSelectedItem());
    }

    @Override
    public void setKey(HillKey key) {
        int size = key.getKey().length;
        changeMatrixSize(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                JFormattedTextField textField = (JFormattedTextField) matrixView.getComponent(i * size + j);
                textField.setText(String.valueOf(key.getKey()[i][j]));
            }
    }
}
