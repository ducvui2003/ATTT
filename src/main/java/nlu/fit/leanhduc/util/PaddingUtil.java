package nlu.fit.leanhduc.util;

import java.util.Arrays;

public class PaddingUtil {

    public static byte[] handleNoPaddingEncrypt(byte[] inputBytes, int blockSize) {
        int paddingLength = blockSize - (inputBytes.length % blockSize);
        return Arrays.copyOf(inputBytes, inputBytes.length + paddingLength);
    }

    public static byte[] handleNoPaddingDecrypt(byte[] inputBytes) {
        int endIndex = inputBytes.length - 1;
        while (endIndex > 0 && inputBytes[endIndex] != 0x00) {
            endIndex--;
        }
        return Arrays.copyOf(inputBytes, endIndex);
    }
}
