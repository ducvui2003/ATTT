package nlu.fit.leanhduc.service.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffineKey implements IKeyDisplay {
    private int a;
    private int b;

    @Override
    public String display() {
        return "AffineKey{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
