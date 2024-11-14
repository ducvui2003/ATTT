package nlu.fit.leanhduc.service.key;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AffineKey implements IKeyDisplay, Serializable {
    private int a;
    private int b;

    @Override
    public String display() {
        return "a: " + a + "\n" + "b: " + b;
    }
}
