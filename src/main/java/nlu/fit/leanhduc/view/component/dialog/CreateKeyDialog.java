package nlu.fit.leanhduc.view.component.dialog;

import nlu.fit.leanhduc.service.key.IKeyDisplay;

import java.awt.*;

public abstract class CreateKeyDialog extends CustomDialog {
    protected IKeyDisplay key;

    public CreateKeyDialog(Frame owner) {
        super(owner, "Nhập khóa", ModalityType.APPLICATION_MODAL);
    }


}
