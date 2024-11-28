package nlu.fit.leanhduc.service.cipher;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.util.constraint.*;

/**
 * Class {@code Algorithm }
 * <p>
 * Đại diện cho các thuật toán mã hóa </p>
 * <p>
 * Chứa các cấu hình về mode, padding, keySize, ivSize, hashFunction, provider
 * để lấy ra thuật toán Java hỗ trợ
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Algorithm {
    /**
     * Tên thuật toán
     */
    String cipher;
    /**
     * Kích thước block
     */
    int blockSize;
    /**
     * Mode
     */
    String mode;
    /**
     * Padding
     */
    String padding;
    /**
     * Kích thước key
     * đơn vị: bit
     */
    int keySize;
    /**
     * Kích thước iv
     * đơn vị: bit
     */
    int ivSize;
    /**
     * Hàm băm
     */
    String hashFunction;
    /**
     * Provider cung cấp thuật toán
     */
    String provider;


    private Algorithm(String cipher, int keySize, String hashFunction) {
        this.cipher = cipher;
        this.keySize = keySize;
        this.hashFunction = hashFunction;
    }

    public Algorithm(String cipher, String mode, String padding, int keySize, int ivSize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }


    private Algorithm(String cipher, String mode, String padding, int keySize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }

    private Algorithm(String cipher, int blockSize, String mode, String padding, int keySize, int ivSize, String provider) {
        this.cipher = cipher;
        this.blockSize = blockSize;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
        this.provider = provider;
    }

    public static Algorithm of(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        return new Algorithm(cipher.getName(), cipher.getBlockSize(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getBit(), cipher.getProvider());
    }

    public static Algorithm of(String cipher, Size keySize, Hash hashFunction) {
        return new Algorithm(cipher, keySize.getBit(), hashFunction.getHashWithDigitalSignature());
    }

    public static Algorithm of(Cipher cipher, Mode mode, Padding padding, Size keySize) {
        return new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.cipher != null) {
            result.append(this.cipher);
        }
        if (this.mode != null) {
            result.append("/").append(this.mode);
        }
        if (this.padding != null) {
            result.append("/").append(this.padding);
        }
        return result.toString();
    }

    public String toSignature() {
        return this.hashFunction + "with" + this.cipher;
    }
}
