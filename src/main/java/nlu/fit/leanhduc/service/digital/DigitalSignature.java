package nlu.fit.leanhduc.service.digital;

import nlu.fit.leanhduc.service.cipher.AbsCipherNative;
import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.key.KeySignature;
import nlu.fit.leanhduc.util.CipherException;

import java.io.IOException;
import java.security.*;

public class DigitalSignature extends AbsCipherNative<KeySignature> {
    SecureRandom secureRandom;
    KeyPair keyPair;
    Signature signature;


    public DigitalSignature(Algorithm algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
        super(algorithm);
        this.algorithm = algorithm;
        this.key = new KeySignature();
        this.key.setAlgorithm(algorithm.toString());
        this.key.setSize(algorithm.getKeySize());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.key.getAlgorithm());
        String DEFAULT_SECURE_RANDOM = "SHA1PRNG";
        secureRandom = SecureRandom.getInstance(DEFAULT_SECURE_RANDOM);
        keyPairGenerator.initialize(this.key.getSize(), secureRandom);
        keyPair = keyPairGenerator.generateKeyPair();
        signature = Signature.getInstance(this.algorithm.toSignature());
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

    }

    @Override
    public void saveKey(String dest) throws IOException, CipherException {

    }

    @Override
    public String encrypt(String plainText) throws CipherException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        throw new UnsupportedOperationException();
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
    public boolean verify(String message) throws InvalidKeyException, SignatureException {
        signature.initVerify(this.getKey().getPublicKey());
        byte[] data = message.getBytes();
        byte[] signData = conversionStrategy.convert(message);
        signature.update(data);
        return signature.verify(signData);
    }

    @Override
    public String signFile(String src) {
        return super.signFile(src);
    }

    @Override
    public boolean verifyFile(String src) {
        return super.verifyFile(src);
    }
}
