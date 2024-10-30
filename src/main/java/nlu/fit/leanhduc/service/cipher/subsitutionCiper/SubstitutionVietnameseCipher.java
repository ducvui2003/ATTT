package nlu.fit.leanhduc.service.cipher.subsitutionCiper;

import nlu.fit.leanhduc.util.VietnameseAlphabetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstitutionVietnameseCipher extends SubstitutionCipher {
    @Override
    public Map<Character, Character> generateKey() {
        List<Character> alphabet = VietnameseAlphabetUtil.generateAlphabet(false);
        List<Character> mappingAlphabet = VietnameseAlphabetUtil.generateAlphabet(true);

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
        return key;
    }
}
