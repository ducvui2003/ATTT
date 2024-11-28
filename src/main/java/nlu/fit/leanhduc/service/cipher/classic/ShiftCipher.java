package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.service.key.classic.ShiftKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.Random;


/**
 * Class {@code ShiftCipher } implement lại thuật toán Dịch chuyển
 * <p>
 * Thuật toán ShiftCipher là dạng thuật toán dịch chuyển
 * Sử dụng bằng cách dịch chuyển mỗi ký tự trong chuỗi một số bước k
 * <p>Công thức mã hóa: <b>Encrypt (x) = (x + k) mod m</b></p>
 * <p>Công thức giải mã: <b>Decrypt (x) = (x - k) mod m</b></p>
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Getter
@Setter
public class ShiftCipher extends AbsClassicCipher<ShiftKeyClassic> {
    /**
     * Số bước dịch chuyển
     */
    protected Integer shift;
    protected Random rd;

    public ShiftCipher(AlphabetUtil alphabet) {
        super(alphabet);
        this.rd = new Random();
    }

    /**
     * Khởi tạo key cho thuật toán
     * <p>Mặc định đối với Shift, khởi tạo khóa với k là một số ngẫu nhiên trong khoảng [1, m]</p>
     */
    @Override
    public ShiftKeyClassic generateKey() {
        return new ShiftKeyClassic(rd.nextInt(alphabetUtil.getLength()) + 1);
    }

    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(ShiftKeyClassic key) throws CipherException {
        super.loadKey(key);
        this.shift = key.getKey();
    }

    /**
     * Mã hóa văn bản bằng thuật toán Shift
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char ce = c;
            // Kiểm tra ký tự có phải là chữ cái không
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                int index = alphabetUtil.indexOf(Character.toLowerCase(c));
                // Dịch chuyển k bước
                int indexOfEncrypt = index + this.shift;
                ce = alphabetUtil.getChar(indexOfEncrypt, isLower);
            }
            result += ce;
        }
        return result;
    }


    /**
     * Giải mã văn bản bằng thuật toán Shift
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < encryptText.length(); i++) {
            char c = encryptText.charAt(i);
            char ce = c;
            // Kiểm tra ký tự có phải là chữ cái không
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                int index = alphabetUtil.indexOf(Character.toLowerCase(c));
                int indexOfDecrypt;
                // Dịch chuyển k bước
                if (index - this.shift < 0)
                    indexOfDecrypt = (index - this.shift + alphabetUtil.getLength()) % alphabetUtil.getLength();
                else
                    indexOfDecrypt = (index - this.shift) % alphabetUtil.getLength();
                ce = alphabetUtil.getChar(indexOfDecrypt, isLower);
            }
            result += ce;
        }
        return result;
    }
}
