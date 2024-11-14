package nlu.fit.leanhduc.service.key;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.util.MatrixUtil;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class HillKey implements IKeyDisplay, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    int[][] key;

    public HillKey(int[][] key) {
        this.key = key;
    }

    @Override
    public String display() {
        return MatrixUtil.displayMatrix(key);
    }
}
