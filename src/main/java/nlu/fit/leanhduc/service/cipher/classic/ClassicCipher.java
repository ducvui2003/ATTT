package nlu.fit.leanhduc.service.cipher.classic;


import lombok.Getter;
import nlu.fit.leanhduc.service.key.SubstitutionKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
public class ClassicCipher extends AbsClassicCipher<SubstitutionKey> {
    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;

    public ClassicCipher(AlphabetUtil alphabetUtil) {
        super(alphabetUtil);
    }

    @Override
    public SubstitutionKey generateKey() {
        List<Character> alphabet = this.alphabetUtil.generateAlphabet(false);
        List<Character> mappingAlphabet = this.alphabetUtil.generateAlphabet(true);

        for (int i = 0; i < alphabet.size(); i++) {
            if (alphabet.get(i).equals(mappingAlphabet.get(i))) {
                int swapIndex = (i + 1) % alphabet.size();
                char temp = mappingAlphabet.get(i);
                mappingAlphabet.set(i, mappingAlphabet.get(swapIndex));
                mappingAlphabet.set(swapIndex, temp);
            }
        }

        Map<Character, Character> key = new HashMap<>();
        for (int i = 0; i < alphabet.size(); i++) {
            key.put(alphabet.get(i), mappingAlphabet.get(i));
        }
        return new SubstitutionKey(key);
    }

    @Override
    public void loadKey(SubstitutionKey key) throws CipherException {
        super.loadKey(key);
        this.encryptMap = key.getKey();
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
