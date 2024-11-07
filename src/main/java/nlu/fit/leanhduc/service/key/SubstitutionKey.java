package nlu.fit.leanhduc.service.key;

import lombok.Getter;

import java.util.Map;

@Getter
public class SubstitutionKey implements IKeyDisplay {
    Map<Character, Character> key;

    public SubstitutionKey(Map<Character, Character> key) {
        this.key = key;
    }

    @Override
    public String display() {
        String result = "";
        for (Map.Entry<Character, Character> entry : key.entrySet()) {
            result += entry.getKey() + " -> " + entry.getValue() + "\n";
        }
        return result;
    }
}
