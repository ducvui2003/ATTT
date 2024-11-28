package nlu.fit.leanhduc.service.key.classic;

import lombok.*;
import nlu.fit.leanhduc.util.constraint.Cipher;

import java.io.Serializable;

/**
 * Class {@code AffineKeyClassic}
 * <p>
 * Class đại diện cho khóa của thuật toán Affine
 * </p>
 */
@Getter
@Setter
public class AffineKeyClassic implements IKeyClassic, Serializable {
    private int a;
    private int b;

    public AffineKeyClassic() {
    }

    public AffineKeyClassic(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Cipher name() {
        return Cipher.AFFINE;
    }
}
