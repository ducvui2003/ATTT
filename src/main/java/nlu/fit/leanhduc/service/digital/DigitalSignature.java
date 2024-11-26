package nlu.fit.leanhduc.service.digital;

import nlu.fit.leanhduc.service.cipher.AbsCipherNative;
import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.key.KeySignature;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class DigitalSignature extends AbsCipherNative<KeySignature> {
    SecureRandom secureRandom;
    KeyPair keyPair;
    Signature signature;
    KeyFactory keyFactory;

    public DigitalSignature(String base64PublicKey, String base64PrivateKey, Algorithm algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(algorithm);
        this.algorithm = algorithm;
        this.key = new KeySignature();
        this.key.setAlgorithm(algorithm.toString());
        this.key.setSize(algorithm.getKeySize());
        this.key.setHashFunction(this.algorithm.toSignature());
        this.keyFactory = KeyFactory.getInstance(this.key.getAlgorithm());
        this.key.setPublicKey(keyFactory, this.conversionStrategy.convert(base64PublicKey));
        this.key.setPrivateKey(keyFactory, this.conversionStrategy.convert(base64PrivateKey));
        this.signature = Signature.getInstance(this.key.getHashFunction());
    }

    public DigitalSignature(Algorithm algorithm) throws NoSuchAlgorithmException {
        super(algorithm);
        this.algorithm = algorithm;
        this.key = new KeySignature();
        this.key.setAlgorithm(algorithm.toString());
        this.key.setSize(algorithm.getKeySize());
        this.key.setHashFunction(this.algorithm.toSignature());
        String DEFAULT_SECURE_RANDOM = "SHA1PRNG";
        this.secureRandom = SecureRandom.getInstance(DEFAULT_SECURE_RANDOM);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.key.getAlgorithm());
        keyPairGenerator.initialize(this.key.getSize(), secureRandom);
        this.keyPair = keyPairGenerator.generateKeyPair();
        this.keyFactory = KeyFactory.getInstance(this.key.getAlgorithm());
        this.signature = Signature.getInstance(this.key.getHashFunction());
    }

    @Override
    public void loadKey(KeySignature key) throws CipherException {
        this.key = key;
    }

    @Override
    public KeySignature generateKey() {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        KeySignature keySignature = new KeySignature();
        keySignature.setPrivateKey(privateKey);
        keySignature.setPublicKey(publicKey);
        keySignature.setSize(this.key.getSize());
        return keySignature;
    }

    @Override
    public void loadKey(String src) throws IOException, CipherException {
        this.key = FileUtil.loadKeySignature(src);
    }

    @Override
    public void saveKey(String dest) throws IOException, CipherException {
        FileUtil.saveKeySignature(this.key, dest);
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        throw new UnsupportedOperationException();
    }

    public void setPrivateKey(String privateKey) throws InvalidKeySpecException {
        this.key.setPrivateKey(this.keyFactory, this.conversionStrategy.convert(privateKey));
    }

    public void setPublicKey(String publicKey) throws InvalidKeySpecException {
        this.key.setPublicKey(this.keyFactory, this.conversionStrategy.convert(publicKey));
    }

    @Override
    public String sign(String message) throws SignatureException, InvalidKeyException {
        byte[] data = message.getBytes();
        signature.initSign(this.key.getPrivateKey());
        signature.update(data);
        byte[] sign = signature.sign();
        return conversionStrategy.convert(sign);
    }

    @Override
    public boolean verify(String message, String signature) throws InvalidKeyException, SignatureException {
        this.signature.initVerify(this.getKey().getPublicKey());
        byte[] data = message.getBytes();
        byte[] signData = conversionStrategy.convert(signature);
        this.signature.update(data);
        return this.signature.verify(signData);
    }

    @Override
    public String signFile(String src) throws InvalidKeyException, IOException, SignatureException {
        signature.initSign(this.key.getPrivateKey());
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buffer = new byte[1024];
        int read;
        while ((read = bis.read(buffer)) != -1) {
            signature.update(buffer, 0, read);
        }
        bis.close();

        byte[] sign = signature.sign();
        return this.conversionStrategy.convert(sign);
    }

    @Override
    public boolean verifyFile(String src, String signature) throws InvalidKeyException, IOException, SignatureException {
        this.signature.initVerify(this.key.getPublicKey());
        byte[] signData = this.conversionStrategy.convert(signature);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buffer = new byte[1024];
        int read;
        while ((read = bis.read(buffer)) != -1) {
            this.signature.update(buffer, 0, read);
        }
        bis.close();
        return this.signature.verify(signData);
    }
}
