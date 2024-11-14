package nlu.fit.leanhduc.service.key;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class ShiftKey implements IKeyDisplay, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Integer key;

    public ShiftKey(int key) {
        this.key = key;
    }

    @Override
    public String display() {
        return key.toString();
    }
}
