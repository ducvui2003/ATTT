package nlu.fit.leanhduc.util.convert;

/**
 * Interface {@code ByteConversionStrategy}
 * <p>
 * Interface định nghĩa chiến lược chuyển đổi giữa byte và String
 * </p>
 */
public interface ByteConversionStrategy {
    /**
     * Chuyển đổi mảng byte thành chuỗi
     *
     * @param data mảng byte cần chuyển đổi
     * @return chuỗi sau khi chuyển đổi
     */
    String convert(byte[] data);

    /**
     * Chuyển đổi chuỗi thành mảng byte
     *
     * @param data chuỗi cần chuyển đổi
     * @return mảng byte sau khi chuyển đổi
     */
    byte[] convert(String data);
}
