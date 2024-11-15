package nlu.fit.leanhduc.service.key;

import lombok.Data;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Data
public class KeySymmetric {
    SecretKey secretKey;
    IvParameterSpec iv;
    private String cipherAlgorithm;
    private byte[] encodedSecretKey;
    private byte[] encodedIv;

    @Override
    public String toString() {
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return byteConversionStrategy.convert(secretKey.getEncoded()) + "_" + byteConversionStrategy.convert(iv.getIV());
    }


    public SecretKey getSecretKey() {
        return new SecretKeySpec(encodedSecretKey, cipherAlgorithm); // Recreate SecretKey
    }

    public IvParameterSpec getIv() {
        return new IvParameterSpec(encodedIv); // Recreate IvParameterSpec
    }

}
