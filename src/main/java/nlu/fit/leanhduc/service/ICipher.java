package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

/**
 * Interface {@code ICipher} định nghĩa các phương thức cơ bản của một thuật toán mã hóa
 *
 * @param <T> kiểu dữ liệu của khóa
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
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

    /**
     * Tiến hành tạo chữ ký cho văn bản
     *
     * @param message văn bản cần ký
     * @return chữ ký cho {@code message}
     * @throws SignatureException  nếu chữ ký không hợp lệ
     * @throws InvalidKeyException nếu khóa không hợp lệ
     * @implSpec Mặc định, hàm này sẽ ném exception {@code UnsupportedOperationException}
     * Hàm này được override bởi class thuật toán sử dụng để hiện thực chữ ký điện tử
     */
    default String sign(String message) throws SignatureException, InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tiến hành xác thực chữ ký dựa vào văn bản đã ký
     *
     * @param message   văn bản cần xác thực
     * @param signature chữ ký
     * @return kết quả xác thực {@code true} nếu chữ ký hợp lệ, {@code false} nếu không hợp lệ
     * @throws InvalidKeyException nếu khóa không hợp lệ
     * @throws SignatureException  nếu chữ ký không hợp lệ
     * @implSpec Mặc định, hàm này sẽ ném exception {@code UnsupportedOperationException}
     * Hàm này được override bởi class thuật toán sử dụng để hiện thực chữ ký điện tử
     */
    default boolean verify(String message, String signature) throws InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tiến hành tạo chữ ký cho file
     *
     * @param src đường dẫn file cần tạo chữ ký
     * @return chữ ký cho {@code src}
     * @throws InvalidKeyException nếu khóa không hợp lệ
     * @throws SignatureException  nếu chữ ký không hợp lệ
     * @throws IOException         nếu file không tồn tại
     * @implSpec Mặc định, hàm này sẽ ném exception {@code UnsupportedOperationException}
     * Hàm này được override bởi class thuật toán sử dụng để hiện thực chữ ký điện tử
     */
    default String signFile(String src) throws InvalidKeyException, IOException, SignatureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tiến hành xác thực chữ ký dựa vào file đã ký
     *
     * @param src       đường dẫn file cần xác thực
     * @param signature chữ ký cần xác thực
     * @return kết quả xác thực {@code true} nếu chữ ký hợp lệ, {@code false} nếu không hợp lệ
     * @throws InvalidKeyException nếu khóa không hợp lệ
     * @throws SignatureException  nếu chữ ký không hợp lệ
     * @implSpec Mặc định, hàm này sẽ ném exception {@code UnsupportedOperationException}
     * Hàm này được override bởi class thuật toán sử dụng để hiện thực chữ ký điện tử
     */
    default boolean verifyFile(String src, String signature) throws InvalidKeyException, IOException, SignatureException {
        throw new UnsupportedOperationException();
    }
}
