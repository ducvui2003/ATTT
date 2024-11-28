package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import nlu.fit.leanhduc.service.key.classic.AffineKeyClassic;
import nlu.fit.leanhduc.service.key.classic.IKeyClassic;
import nlu.fit.leanhduc.service.key.classic.SubstitutionKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.Random;

/**
 * Class {@code AffineCipher } implement lại thuật toán Affine
 * <p>
 * Thuật toán Affine là một dạng thuật toán thay thế dùng một bảng chũ cái,
 * trong đó mỗi chữ cái được ánh xạ qua một số vầ được mã hóa qua một hàm số học
 * <p>Công thức mã hóa: <b>Encrypt (x) = (a * x + b) mod m</b></p>
 * <p>Công thức giải mã: <b>Decrypt (x) = a^(-1) * (x - b)</b></p>
 * <quote>
 * Lưu ý: a và n là 2 só nguyên tố cùng nhau
 * </quote>
 * </p>
 * <p> Định nghĩa các method cơ bản của một thuật toán mã hóa cổ điển
 * Hiện thực hàm {@code loadKey} và {@code saveKey} của interface {@code ICipher} </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Getter
public class AffineCipher extends AbsClassicCipher<AffineKeyClassic> {

    protected Random rd;
    /**
     * Phạm vi của bảng chữ cái
     */
    protected int range;

    public AffineCipher(AlphabetUtil alphabetUtil) {
        super(alphabetUtil);
        this.rd = new Random();
        // Gán phạm vi bảng chữ cái đầu vào
        this.range = this.alphabetUtil.getLength();
    }

    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(AffineKeyClassic key) throws CipherException {
        // Kiểm tra khóa có hợp lệ không?
        // a và m phải là 2 số nguyên tố cùng nhau (Ước chung lớn nhất của a và b là 1)
        if (ModularUtil.findGCD(key.getA(), this.alphabetUtil.getLength()) != 1)
            throw new CipherException("Ước chung lớn nhất của a và b phải bằng 1");
        super.loadKey(key);
    }

    @Override
    public AffineKeyClassic generateKey() {
        AffineKeyClassic key = new AffineKeyClassic();
        int a;
        do {
            a = rd.nextInt(1, this.range);
        } while (ModularUtil.findGCD(a, this.range) != 1);
        int b = rd.nextInt(1, this.range);
        key.setA(a);
        key.setB(b);
        return key;
    }

    /**
     * Mã hóa chuỗi,
     * thực hiện ánh xạ bảng chữ cái thông qua hàm số học
     * <p>Công thức mã hóa: <b>Encrypt (x) = (a * x + b) mod m</b></p>
     *
     * @param plainText bản mã
     * @return bản rõ
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        char[] plainTextArray = plainText.toCharArray();
        String result = "";
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                // Lưu trạng thái upper hoặc lower của ký tự
                boolean isLower = Character.isLowerCase(c);
                //Encrypt (x) = (a * x + b) mod m
                int index = this.alphabetUtil.indexOf(c);
                int charEncrypt = this.key.getA() * index + this.key.getB();
                result += alphabetUtil.getChar(charEncrypt, isLower);
            } else
                result += c;
        }
        return result;
    }

    /**
     * Mã hóa chuỗi,
     * thực hiện ánh xạ bảng chữ cái thông qua hàm số học
     * <p>Công thức mã hóa: <b>Decrypt (x) = a^(-1) * (x - b)</b></p>
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        char[] plainTextArray = encryptText.toCharArray();
        String result = "";
        int modularInverse = ModularUtil.modularInverse(this.key.getA(), this.range);
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Decrypt (x) = a^(-1) * (x - b)
                int index = this.alphabetUtil.indexOf(c);
                int charDecrypt = modularInverse * (index - this.key.getB());
                result += alphabetUtil.getChar(charDecrypt, isLower);
            } else
                result += c;
        }
        return result;
    }
}
