package nlu.fit.leanhduc.service.hash;

import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Class {@code AbsHash}
 * <p>
 * Class đại diện cho các thuật toán băm hỗ trợ
 * </p>
 * <p>
 * Cung cấp các phương thức băm thông điệp, file và băm kèm HMAC
 * </p>
 */
public abstract class AbsHash {

    protected ByteConversionStrategy byteConversionStrategy;

    public AbsHash() {
        this.byteConversionStrategy = new Base64ConversionStrategy();
    }

    public abstract String hash(String message) throws NoSuchAlgorithmException;

    public abstract String hashWithHMAC(String message, String key) throws NoSuchAlgorithmException;

    public abstract String hashFile(String src) throws NoSuchAlgorithmException, IOException;

    public abstract String hashFileWithHMAC(String src, String key) throws NoSuchAlgorithmException, IOException;

}
