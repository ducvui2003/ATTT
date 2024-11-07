package nlu.fit.leanhduc.service.key;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.util.MatrixUtil;

@Setter
@Getter
public class HillKey implements IKeyDisplay {
    int[][] key;

    public HillKey(int[][] key) {
        this.key = key;
    }

    @Override
    public String display() {
        return MatrixUtil.displayMatrix(key);
    }
}
