package nlu.fit.leanhduc.view.component;

import javax.swing.*;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class SwingComponentUtil {
    public static JFormattedTextField createFormatTextFieldNumber(
            int columns,
            int minimum,
            int maximum,
            int defaultValue,
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
}
