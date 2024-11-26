package nlu.fit.leanhduc.service.cipher.classic;

import nlu.fit.leanhduc.service.key.HillKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.MatrixUtil;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HillCipher extends AbsClassicCipher<HillKey> {

    public HillCipher(AlphabetUtil alphabet) {
        super(alphabet);
    }

    @Override
    public HillKey generateKey() {
        int size = 3;
        int[][] key = new int[size][size];
        do {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    key[i][j] = (int) (Math.random() * alphabetUtil.getLength());
                }
            }
        } while (MatrixUtil.determinant(key) == 0);
        return new HillKey(key);
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        String result = "";
        int lengthText = plainText.length();
        int lengthKey = this.key.getKey().length;
        int start = 0;
        while (start < lengthText) {
            int[] plainTextArr = new int[lengthKey];
            boolean[] isLower = new boolean[lengthKey];
            int lengthKeyTemp = 0;
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
            int[] encryptArr = MatrixUtil.multiMatrix(plainTextArr, this.key.getKey());
            lengthKeyTemp = 0;
            for (int i = 0; i < lengthKey; i++) {
                result += alphabetUtil.getChar(encryptArr[i], isLower[lengthKeyTemp]);
            }
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        String result = "";
        int lengthText = encryptText.length();
        int lengthKey = this.key.getKey().length;
        int[][] inverseKey = findInverseKey();
        int start = 0;
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
     * @param
     * @return the amount of health hero has after attack
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
