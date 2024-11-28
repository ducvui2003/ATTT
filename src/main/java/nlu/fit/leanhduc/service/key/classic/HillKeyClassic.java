package nlu.fit.leanhduc.service.key.classic;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.util.constraint.Cipher;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class HillKeyClassic implements IKeyClassic, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    int[][] key;

    public HillKeyClassic(int[][] key) {
        this.key = key;
    }

    @Override
    public Cipher name() {
        return Cipher.HILL;
    }
}
