package nlu.fit.leanhduc.service.key;

import lombok.Getter;

import java.util.List;

@Getter
public class VigenereKey implements IKeyDisplay {
    List<Integer> key;

    public VigenereKey(List<Integer> key) {
        this.key = key;
    }

    @Override
    public String display() {
        return "VigenereKey{" +
                "key=" + key +
                '}';
    }
}
