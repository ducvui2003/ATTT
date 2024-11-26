package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Interface định nghĩa các phương thức cơ bản của một thuật toán mã hóa
 *
 * @param <T> kiểu dữ liệu của khóa
 */
public interface ICipher<T> {
    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    void loadKey(T key) throws CipherException;

    /**
     * Tạo khóa cho thuật toán
     *
     * @return khóa
     */
    T generateKey();

    /**
     * Tải khóa từ src lên và gán cho thuật toán
     *
     * @param src đường dẫn file chứa khóa
     */
    void loadKey(String src) throws IOException, CipherException;

    /**
     * Lưu khá xuống file chỉ định
     *
     * @param dest đường dẫn lưu file khóa
     */
    void saveKey(String dest) throws IOException, CipherException;

    /**
     * Mã hóa chuỗi
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    String encrypt(String plainText) throws CipherException;

    /**
     * Mã hóa chuỗi
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    String decrypt(String encryptText) throws CipherException;

    /**
     * Mã hóa file
     *
     * @param src  file cần mã hóa
     * @param dest nơi lưu file cần mã hóa
     * @return bản rõ
     */
    default boolean encrypt(String src, String dest) throws CipherException {
        throw new UnsupportedOperationException();
    }

    /**
     * Giải hóa file
     *
     * @param src  file cần giải mã
     * @param dest nơi lưu file đã giải mã
     * @return bản rõ
     */
    default boolean decrypt(String src, String dest) throws CipherException {
        throw new UnsupportedOperationException();
    }

    default String sign(String message) throws SignatureException, InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    default boolean verify(String message, String signature) throws InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException();
    }

    default String signFile(String src) throws InvalidKeyException, IOException, SignatureException {
        throw new UnsupportedOperationException();
    }

    default boolean verifyFile( String src,String signature) throws InvalidKeyException, IOException, SignatureException {
        throw new UnsupportedOperationException();
    }
}
