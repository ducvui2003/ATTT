package nlu.fit.leanhduc.view.component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class SwingComponentUtil {
    public static JFormattedTextField createFormatTextFieldNumber(
            int columns,
            int minimum,
            Integer maximum,
            Integer defaultValue,
            boolean allowsInvalid,
            boolean commitsOnValidEdit
    ) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(allowsInvalid);
        numberFormatter.setMinimum(minimum);
        numberFormatter.setMaximum(maximum);
        numberFormatter.setCommitsOnValidEdit(commitsOnValidEdit);

        JFormattedTextField inputKeyLength = new JFormattedTextField(numberFormatter);
        inputKeyLength.setColumns(columns);
        inputKeyLength.setValue(defaultValue);
        return inputKeyLength;
    }

    public static void addComponentGridBag(JPanel container, GridBagConstraints gbc, int x, int y, int width, int height, Component component) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.VERTICAL;
        container.add(component, gbc);
    }

    public static void addComponentGridBag(Container container, GridBagConstraintsBuilder gbc, Component component) {
        container.add(component, gbc.getGbc());
    }


    public static JSeparator createSeparator() {
        return createSeparator(JSeparator.HORIZONTAL, Color.BLACK, 2);
    }

    public static JSeparator createSeparator(int orientation, Color color, int thickness) {
        JSeparator boldSeparator = new JSeparator(orientation);
        boldSeparator.setBorder(new LineBorder(color, thickness));
        return boldSeparator;
    }
}
