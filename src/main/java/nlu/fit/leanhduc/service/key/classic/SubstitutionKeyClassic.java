package nlu.fit.leanhduc.service.key.classic;

import lombok.Getter;
import nlu.fit.leanhduc.util.constraint.Cipher;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class {@code SubstitutionKeyClassic}
 * <p>
 * Class đại diện cho khóa của thuật toán Substitution
 * </p>
 */
@Getter
public class SubstitutionKeyClassic implements IKeyClassic, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Map<Character, Character> key;

    public SubstitutionKeyClassic(Map<Character, Character> key) {
        this.key = key;
    }

    @Override
    public Cipher name() {
        return Cipher.SUBSTITUTION;
    }
}
