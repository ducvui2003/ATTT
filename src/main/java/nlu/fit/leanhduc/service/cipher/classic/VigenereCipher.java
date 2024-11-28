package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.service.key.classic.ViginereKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class {@code VigenereCipher } implement lại thuật toán thay thế
 * <p>
 * Thuật toán ShiftCipher là dạng thuật toán thay thế
 * Mỗi phần tử trong bản rõ sẽ được ánh xạ duy nhất với một phần tử trong bản mã
 * với khóa K là một mảng các ký tự
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Getter
@Setter
public class VigenereCipher extends AbsClassicCipher<ViginereKeyClassic> {
    protected List<Integer> keys;
    /**
     * Độ dài của khóa
     */
    protected int keyLength;
    protected Random rd = new Random();

    public VigenereCipher(AlphabetUtil alphabetUtil) {
        super(alphabetUtil);
    }


    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(ViginereKeyClassic key) throws CipherException {
        super.loadKey(key);
        this.keyLength = key.getKey().size();
    }

    /**
     * Khởi tạo key cho thuật toán
     * <p>Mặc định đối với Vigenere, khởi tạo khóa với k là một số ngẫu nhiên trong khoảng [1, m]</p>
     */
    @Override
    public ViginereKeyClassic generateKey() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            result.add(rd.nextInt(alphabetUtil.getLength() - 1));
        return new ViginereKeyClassic(result);
    }

    /**
     * Mã hóa văn bản bằng thuật toán Vigenere
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (this.keys == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char cShifted = encryptLetter(this.keys.get(i % keyLength), c);
            result += cShifted;
        }
        return result;
    }

    /**
     * Giải mã văn bản bằng thuật toán Vigenere
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (this.keys == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < encryptText.length(); i++) {
            char c = encryptText.charAt(i);
            char cShifted = decryptLetter(this.keys.get(i % keyLength), c);
            result += cShifted;
        }
        return result;
    }

    /**
     * Mã hóa ký tự
     *
     * @param shift số bước dịch chuyển
     * @param ch    ký tự cần giải mã
     * @return ký tự đã được giải mã
     */
    private char encryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            boolean isLower = Character.isLowerCase(ch);
            int index = alphabetUtil.indexOf(Character.toLowerCase(ch));
            int indexOfEncrypt = index + shift;
            result = alphabetUtil.getChar(indexOfEncrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }

    /**
     * Giải mã ký tự
     *
     * @param shift số bước dịch chuyển
     * @param ch    ký tự cần mã hóa
     * @return ký tự đã được mã hóa
     */
    private char decryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            boolean isLower = Character.isLowerCase(ch);
            int index = alphabetUtil.indexOf(Character.toLowerCase(result));
            int indexOfDecrypt;
            if (index - shift < 0)
                indexOfDecrypt = (index - shift + alphabetUtil.getLength()) % alphabetUtil.getLength();
            else
                indexOfDecrypt = (index - shift) % alphabetUtil.getLength();
            result = alphabetUtil.getChar(indexOfDecrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }

}
