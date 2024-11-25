package nlu.fit.leanhduc.service.key;

import lombok.Data;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Data
public class KeyAsymmetric {
    PublicKey publicKey;
    PrivateKey privateKey;
    String cipher;
    String mode;
    String padding;
    //  đơn vị: bit
    int keySize;

    public void setPublicKey(KeyFactory keyFactory, String base64) throws InvalidKeySpecException {
        ByteConversionStrategy conversionStrategy = new Base64ConversionStrategy();
        X509EncodedKeySpec keySpecPublicKey = new X509EncodedKeySpec(conversionStrategy.convert(base64));
        this.publicKey = keyFactory.generatePublic(keySpecPublicKey);
    }

    public void setPublicKey(KeyFactory keyFactory, byte[] bytes) throws InvalidKeySpecException {
        X509EncodedKeySpec keySpecPublicKey = new X509EncodedKeySpec(bytes);
        this.publicKey = keyFactory.generatePublic(keySpecPublicKey);
    }

    public void setPrivateKey(KeyFactory keyFactory, String base64) throws InvalidKeySpecException {
        ByteConversionStrategy conversionStrategy = new Base64ConversionStrategy();
        PKCS8EncodedKeySpec keySpecPrivateKey = new PKCS8EncodedKeySpec(conversionStrategy.convert(base64));
        this.privateKey = keyFactory.generatePrivate(keySpecPrivateKey);
    }

    public void setPrivateKey(KeyFactory keyFactory, byte[] bytes) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpecPrivateKey = new PKCS8EncodedKeySpec(bytes);
        this.privateKey = keyFactory.generatePrivate(keySpecPrivateKey);
    }
}
