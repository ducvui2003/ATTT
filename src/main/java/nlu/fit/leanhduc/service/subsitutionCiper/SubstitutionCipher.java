package nlu.fit.leanhduc.service.subsitutionCiper;


import lombok.Getter;
import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.util.CipherException;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class SubstitutionCipher implements IAsymmetricEncrypt<Map<Character, Character>> {
    Map<Character, Character> key;

    @Override
    public void loadKey(Map<Character, Character> key) throws CipherException {
        if (!validationKey(key)) {
            throw new CipherException("Invalid key");
        }
        this.key = key;
    }

    private boolean validationKey(Map<Character, Character> key) {
        Set<Character> keyOfMap = new HashSet<>();
        Set<Character> valueOfMap = new HashSet<>();
        for (Map.Entry<Character, Character> entry : key.entrySet()) {
            keyOfMap.add(entry.getKey());
            valueOfMap.add(entry.getValue());
        }
        return keyOfMap.size() == valueOfMap.size();
    }

    @Override
    public String encrypt(File file) throws CipherException {
        return null;
    }

    @Override
    public String decrypt(File cryptFile) throws CipherException {
        return null;
    }
}
