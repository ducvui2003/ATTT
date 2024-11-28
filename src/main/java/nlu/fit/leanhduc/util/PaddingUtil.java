package nlu.fit.leanhduc.util;

import java.util.Arrays;

/**
 * Class {@code PaddingUtil}
 * <p>
 * Class hỗ trợ xử lý padding cho các thuật toán mã hóa
 * </p>
 */
public class PaddingUtil {

    /**
     * <p>Xử lý padding cho thuật toán mã hóa với mode NoPadding</p>
     *
     * @param inputBytes mảng byte cần xử lý padding
     * @param blockSize  kích thước block
     * @return mảng byte sau khi xử lý padding
     */
    public static byte[] handleNoPaddingEncrypt(byte[] inputBytes, int blockSize) {
        int paddingLength = blockSize - (inputBytes.length % blockSize);
        return Arrays.copyOf(inputBytes, inputBytes.length + paddingLength);
    }

    /**
     * <p>Xử lý padding cho thuật toán giải mã với mode NoPadding</p>
     *
     * @param inputBytes mảng byte cần xử lý padding
     * @return mảng byte sau khi xử lý padding
     */
    public static byte[] handleNoPaddingDecrypt(byte[] inputBytes) {
        int endIndex = inputBytes.length - 1;
        while (endIndex > 0 && inputBytes[endIndex] != 0x00) {
            endIndex--;
        }
        return Arrays.copyOf(inputBytes, endIndex);
    }
}
