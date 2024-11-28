package nlu.fit.leanhduc.view.component;

import java.awt.*;

public class GridBagConstraintsBuilder {

    private final GridBagConstraints gbc;

    private GridBagConstraintsBuilder() {
        this.gbc = new GridBagConstraints();
    }

    public static GridBagConstraintsBuilder builder() {
        return new GridBagConstraintsBuilder();
    }

    public GridBagConstraintsBuilder grid(int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        return this;
    }

    public GridBagConstraintsBuilder gridSpan(int gridwidth, int gridheight) {
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        return this;
    }

    public GridBagConstraintsBuilder weight(double weightx, double weighty) {
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        return this;
    }

    public GridBagConstraintsBuilder anchor(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    public GridBagConstraintsBuilder fill(int fill) {
        gbc.fill = fill;
        return this;
    }

    public GridBagConstraintsBuilder insets(int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GridBagConstraintsBuilder insets(int value) {
        gbc.insets = new Insets(value, value, value, value);
        return this;
    }

    public GridBagConstraintsBuilder ipad(int ipadx, int ipady) {
        gbc.ipadx = ipadx;
        gbc.ipady = ipady;
        return this;
    }

    public GridBagConstraintsBuilder build() {
        return this;
    }

    public GridBagConstraints getGbc() {
        return gbc;
    }
}
