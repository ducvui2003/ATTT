package nlu.fit.leanhduc.service.key.classic;

import lombok.Getter;
import nlu.fit.leanhduc.util.constraint.Cipher;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class {@code ShiftKeyClassic}
 * <p>
 * Class đại diện cho khóa của thuật toán Shift
 * </p>
 */
@Getter
public class ShiftKeyClassic implements IKeyClassic, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Integer key;

    public ShiftKeyClassic(int key) {
        this.key = key;
    }

    @Override
    public Cipher name() {
        return Cipher.SHIFT;
    }
}
