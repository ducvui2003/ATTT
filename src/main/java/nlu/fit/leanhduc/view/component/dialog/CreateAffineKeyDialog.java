package nlu.fit.leanhduc.view.component.dialog;

import nlu.fit.leanhduc.service.key.AffineKey;

import java.awt.*;

public class CreateAffineKeyDialog extends CreateKeyDialog {
    public CreateAffineKeyDialog(Frame owner) {
        super(owner);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(new Label("a"));
        TextField inputA = new TextField(10);
        this.add(inputA);

        this.add(new Label("b"));
        TextField inputB = new TextField(10);
        this.add(inputB);

        Button button = new Button("Tạo khóa");
        this.add(button);

        button.addActionListener(e -> {
            this.key = new AffineKey(Integer.parseInt(inputA.getText()), Integer.parseInt(inputB.getText()));
            this.handleClose();
        });
    }
}
