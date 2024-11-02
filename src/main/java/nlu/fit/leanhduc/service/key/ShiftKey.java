package nlu.fit.leanhduc.service.key;

public class ShiftKey implements IKeyDisplay {
    Integer key;

    public ShiftKey(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    @Override
    public String display() {
        return "ShiftKey{" +
                "key=" + key +
                '}';
    }
}
