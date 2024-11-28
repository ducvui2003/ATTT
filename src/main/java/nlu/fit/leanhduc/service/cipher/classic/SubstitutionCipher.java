package nlu.fit.leanhduc.service.cipher.classic;


import lombok.Getter;
import nlu.fit.leanhduc.service.key.classic.SubstitutionKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.*;

/**
 * Class {@code SubstitutionCipher } implement lại thuật toán thay thế
 * <p>
 * Thuật toán Substitution là một dạng thuật toán thay thế
 * <p>Sử dụng một bảng chữ  cái A để ánh xạ sang các phần từ của bảng chữ cái B</p>
 *
 * <p>Công thức mã hóa: <b>Encrypt (x) = (a * x + b) mod m</b></p>
 * <p>Công thức giải mã: <b>Decrypt (x) = a^(-1) * (x - b)</b></p>
 * <quote>
 * Lưu ý: a và n là 2 só nguyên tố cùng nhau
 * </quote>
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Getter
public class SubstitutionCipher extends AbsClassicCipher<SubstitutionKeyClassic> {

    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;

    public SubstitutionCipher(AlphabetUtil alphabetUtil) {
        super(alphabetUtil);
    }

    @Override
    public SubstitutionKeyClassic generateKey() {
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
        return new SubstitutionKeyClassic(key);
    }

    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(SubstitutionKeyClassic key) throws CipherException {
        super.loadKey(key);
//        Gán key cho thuật toán
        this.encryptMap = key.getKey();
//        Nghịch đỏa key để tạo ra decryptMap
        this.decryptMap = createDecryptMap();
    }


    /**
     * Nghịch đảo encryptMap để tạo ra decryptMap
     */
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

    /**
     * Mã hóa văn bản bằng thuật toán Substitution
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char ch : plainText.toCharArray()) {
            result.append(encryptMap.getOrDefault(ch, ch));
        }
        return result.toString();
    }

    /**
     * Giải mã văn bản bằng thuật toán Substitution
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char ch : encryptText.toCharArray()) {
            result.append(decryptMap.getOrDefault(ch, ch));
        }
        return result.toString();
    }
}
