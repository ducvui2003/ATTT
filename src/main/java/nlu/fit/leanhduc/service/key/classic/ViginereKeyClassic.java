package nlu.fit.leanhduc.service.key.classic;

import lombok.Getter;
import nlu.fit.leanhduc.util.constraint.Cipher;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Class {@code ViginereKeyClassic}
 * <p>
 * Class đại diện cho khóa của thuật toán Viginere
 * </p>
 */
@Getter
public class ViginereKeyClassic implements IKeyClassic, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    List<Integer> key;

    public ViginereKeyClassic(List<Integer> key) {
        this.key = key;
    }

    @Override
    public Cipher name() {
        return Cipher.VIGINERE;
    }
}
