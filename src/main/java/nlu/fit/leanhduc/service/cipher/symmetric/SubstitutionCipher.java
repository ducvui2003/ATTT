package nlu.fit.leanhduc.service.cipher.symmetric;


import lombok.Getter;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.SubstitutionKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
public class SubstitutionCipher implements ISubstitutionCipher<SubstitutionKey> {
    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;
    private AlphabetUtil alphabet;

    public SubstitutionCipher(AlphabetUtil alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public SubstitutionKey generateKey() {
        List<Character> alphabet = this.alphabet.generateAlphabet(false);
        List<Character> mappingAlphabet = this.alphabet.generateAlphabet(true);

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
        if (!validationKey(key.getKey())) {
            throw new CipherException("Invalid key");
        }
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

    @Override
    public boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        return false;
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return false;
    }

    @Override
    public boolean loadKey(String src) throws IOException {
        return false;
    }

    @Override
    public boolean saveKey(String dest) throws IOException {
        return false;
    }
}
