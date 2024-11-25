package nlu.fit.leanhduc.service.key;

import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeySignature {
    PublicKey publicKey;
    PrivateKey privateKey;
    int size; // bit
    String algorithm;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

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
