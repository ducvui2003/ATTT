package nlu.fit.leanhduc.service.key;

public class ShiftKey implements IKeyDisplay {
    Integer key;

    public ShiftKey(int key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    @Override
    public String display() {
        return key.toString();
    }
}
