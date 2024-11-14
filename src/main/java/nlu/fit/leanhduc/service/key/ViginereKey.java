package nlu.fit.leanhduc.service.key;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class ViginereKey implements IKeyDisplay, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    List<Integer> key;

    public ViginereKey(List<Integer> key) {
        this.key = key;
    }

    @Override
    public String display() {
        return "VigenereKey{" +
                "key=" + key +
                '}';
    }
}
