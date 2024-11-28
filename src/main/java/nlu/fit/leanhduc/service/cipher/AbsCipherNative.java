package nlu.fit.leanhduc.service.cipher;

import lombok.Getter;
import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

/**
 * Abstract class {@code AbsCipherNative }
 * <p>
 * Đại diện cho các thuật toán mã hóa Java hỗ trợ
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
public abstract class AbsCipherNative<T> implements ICipher<T> {
    @Getter
    protected T key;
    /**
     * Đối tượng chứa thuật toán mã hóa
     */
    protected Algorithm algorithm;
    /**
     * Đổi tượng chuyển đổi byte -> base64 và ngược lại
     */
    protected ByteConversionStrategy conversionStrategy;

    public AbsCipherNative() {
        this.conversionStrategy = new Base64ConversionStrategy();
    }

    public AbsCipherNative(Algorithm algorithm) {
        this.algorithm = algorithm;
        this.conversionStrategy = new Base64ConversionStrategy();
    }
}
