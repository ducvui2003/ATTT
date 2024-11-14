package nlu.fit.leanhduc.service.key;

import lombok.Data;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Data
public class KeySymmetric {
    SecretKey secretKey;
    IvParameterSpec iv;

    @Override
    public String toString() {
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return byteConversionStrategy.convert(secretKey.getEncoded()) + "_" + byteConversionStrategy.convert(iv.getIV());
    }
}
