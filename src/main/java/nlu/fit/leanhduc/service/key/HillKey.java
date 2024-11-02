package nlu.fit.leanhduc.service.key;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
public class HillKey implements IKeyDisplay {
    int[][] key;

    public HillKey(int[][] key) {
        this.key = key;
    }

    @Override
    public String display() {
        return "HillKey{" +
                "key=" + Arrays.toString(key) +
                '}';
    }
}
