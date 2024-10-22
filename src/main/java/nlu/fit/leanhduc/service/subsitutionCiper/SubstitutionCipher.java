package nlu.fit.leanhduc.service.subsitutionCiper;


import lombok.Getter;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.util.CipherException;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class SubstitutionCipher implements ITextEncrypt, IKeyGenerator<Map<Character, Character>> {
    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;

    @Override
    public void loadKey(Map<Character, Character> key) throws CipherException {
        if (!validationKey(key)) {
            throw new CipherException("Invalid key");
        }
        this.encryptMap = key;
        this.decryptMap = createDecryptMap();
    }

    private Map<Character, Character> createDecryptMap() {
        Map<Character, Character> decryptMap = new HashMap<>();
        for (Map.Entry<Character, Character> entry : encryptMap.entrySet()) {
            decryptMap.put(entry.getValue(), entry.getKey());
        }
        return decryptMap;
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
    public String encrypt(String plainText) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char ch : plainText.toCharArray()) {
            result.append(encryptMap.getOrDefault(ch, ch));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char ch : encryptText.toCharArray()) {
            result.append(decryptMap.getOrDefault(ch, ch));
        }
        return result.toString();
    }
}
