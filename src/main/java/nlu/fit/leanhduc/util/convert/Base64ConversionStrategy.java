package nlu.fit.leanhduc.util.convert;

import java.util.Base64;

/**
 * Class {@code Base64ConversionStrategy}
 * <p>
 * Class chuyển đổi giữa byte và chuỗi sử dụng Base64
 * </p>
 */
public class Base64ConversionStrategy implements ByteConversionStrategy {

    /**
     * Chuyển đổi mảng byte thành chuỗi
     *
     * @param data mảng byte cần chuyển đổi
     * @return chuỗi sau khi chuyển đổi
     */
    @Override
    public String convert(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Chuyển đổi chuỗi thành mảng byte
     *
     * @param data chuỗi cần chuyển đổi
     * @return mảng byte sau khi chuyển đổi
     */
    @Override
    public byte[] convert(String data) {
        return Base64.getDecoder().decode(data);
    }
}
