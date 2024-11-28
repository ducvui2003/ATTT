package nlu.fit.leanhduc.service.cipher.classic;

import nlu.fit.leanhduc.service.key.classic.HillKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.MatrixUtil;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

/**
 * Class {@code HillCipher } implement lại thuật toán Hill
 * <p>
 * Thuật toán Hill là một dạng thuật toán thay thế
 * <p>Sử dụng {@code m} tổ hợp tuyến tính của {@code m} ký tự trong bản rõ để tạo ra m ký tự trong bản mã</p>
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
public class HillCipher extends AbsClassicCipher<HillKeyClassic> {

    public HillCipher(AlphabetUtil alphabet) {
        super(alphabet);
    }


    /**
     * Khởi tạo key cho thuật toán
     * Mặc định đối với hill, khởi tạo khóa với kích thước là ma trận 3x3
     */
    @Override
    public HillKeyClassic generateKey() {
        int size = 3;
        int[][] key = new int[size][size];
        int determinant;
        do {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    key[i][j] = (int) (Math.random() * alphabetUtil.getLength());
                }
            }
            determinant = MatrixUtil.determinant(key);
        } while (determinant == 0 || ModularUtil.findGCD(determinant, this.getAlphabetUtil().getLength()) != 1);
        return new HillKeyClassic(key);
    }

    /**
     * Mã hóa văn bản bằng thuật toán Hill
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        String result = "";
        int lengthText = plainText.length();
        int lengthKey = this.key.getKey().length;
        int start = 0;
        // Kiểm tra số lượng ký tự trong văn bản cần đệm
        int padding = lengthKey - (lengthText % lengthKey);
        lengthText = lengthText + padding;
        // Đệm ký tự vào phần cuối của văn bản
        if (padding != 0) {
            for (int i = 0; i < padding; i++) {
                plainText += this.alphabetUtil.getChar(0);
            }
        }
        while (start < lengthText) {
            int[] plainTextArr = new int[lengthKey];
            boolean[] isLower = new boolean[lengthKey];
            int lengthKeyTemp = 0;
            // Thiết lập ma trận P từ văn bản cần mã hóa
            while (lengthKeyTemp < lengthKey) {
                char text = plainText.charAt(start);
                if (Character.isLetter(text)) {
                    isLower[lengthKeyTemp] = Character.isLowerCase(text);
                    text = Character.toLowerCase(text);
                    plainTextArr[lengthKeyTemp] = alphabetUtil.indexOf(text);
                    lengthKeyTemp++;
                }
                start++;
            }
            // Tiến hành nhân hai ma trận K và P
            int[] encryptArr = MatrixUtil.multiMatrix(plainTextArr, this.key.getKey());
            lengthKeyTemp = 0;
            for (int i = 0; i < lengthKey; i++) {
                result += alphabetUtil.getChar(encryptArr[i], isLower[lengthKeyTemp]);
            }
        }

        if (padding != 0) {
            for (int i = 0; i < padding; i++) {
                result += "=";
            }
        }
        return result;
    }

    /**
     * Giải mã văn bản đã được mã hóa bằng thuật toán Hill
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        String result = "";
        int lengthText = encryptText.length();
        int lengthKey = this.key.getKey().length;
        int[][] inverseKey = findInverseKey();
        int start = 0;
        int padding = lengthKey - (lengthText % lengthKey);
        encryptText = encryptText.replaceAll("=", "");
        lengthText = encryptText.length();
        while (start < lengthText) {
            int[] cipherTextArr = new int[lengthKey];
            boolean[] isLower = new boolean[lengthKey];
            int lengthKeyTemp = 0;
            while (lengthKeyTemp < lengthKey) {
                char text = encryptText.charAt(start);
                if (Character.isLetter(text)) {
                    isLower[lengthKeyTemp] = Character.isLowerCase(text);
                    text = Character.toLowerCase(text);
                    cipherTextArr[lengthKeyTemp] = alphabetUtil.indexOf(text);
                    lengthKeyTemp++;
                }
                start++;
            }
            int[] encryptArr = MatrixUtil.multiMatrix(cipherTextArr, inverseKey);
            lengthKeyTemp = 0;
            for (int i = 0; i < lengthKey; i++) {
                result += alphabetUtil.getChar(encryptArr[i], isLower[lengthKeyTemp]);
            }
        }
        if (padding != 0) {
            result = result.substring(0, result.length() + 1 - padding);
        }
        return result;
    }

    /**
     * <p>Tìm ma trận nghịch đảo K^-1 của khóa K</p>
     * <p> Công thức:
     * K = 1/det(K) * A^T
     * </p>
     * Trong đó:
     * <p>det(K): định thức ma trận vuông K</p>
     * <p>A^T: ma trận phụ hợp đã chuyển vị</p>
     *
     * @return ma trận nghịch đảo K^-1
     */
    private int[][] findInverseKey() {
//        Định thức của khóa K
        int detKey = MatrixUtil.determinant(this.key.getKey());
//        Ma trận chuyển vị của khóa K
        int[][] matrixTransposeKey = MatrixUtil.getMatrixTranspose(MatrixUtil.getMatrixAdjunct(this.key.getKey()));
        int detInverse = ModularUtil.modularInverse(detKey, alphabetUtil.getLength());
        int[][] inverseMatrix = MatrixUtil.multiMatrix(detInverse, matrixTransposeKey);
        return inverseMatrix;
    }
}
