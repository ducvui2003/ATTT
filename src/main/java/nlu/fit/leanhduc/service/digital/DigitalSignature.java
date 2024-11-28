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

/**
 * Class {@code DigitalSignature }
 * <p>
 * Hiện thực các method phục vụ cho việc tạo chữ ký điện tử
 * Mặc định, sử dụng thuật toán DSA để tạo chữ ký và kiểm tra chữ ký
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
public class DigitalSignature extends AbsCipherNative<KeySignature> {
    SecureRandom secureRandom;
    KeyPair keyPair;
    Signature signature;
    KeyFactory keyFactory;

    /**
     * Hàm tạo khóa cho thuật toán
     */
    public DigitalSignature(String base64PublicKey, String base64PrivateKey, Algorithm algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(algorithm);
        this.algorithm = algorithm;
        this.key = new KeySignature();
        this.key.setAlgorithm(algorithm.toString());
        this.key.setSize(algorithm.getKeySize());
        this.key.setHashFunction(this.algorithm.toSignature());
        // Khởi tạo Key Factory với thuật toán đã chọn
        this.keyFactory = KeyFactory.getInstance(this.key.getAlgorithm());
        // Chuyển Public key tử base64 sang dạng PublicKey
        this.key.setPublicKey(keyFactory, this.conversionStrategy.convert(base64PublicKey));
        // Chuyển Private key từ base64 sang dạng PrivateKey
        this.key.setPrivateKey(keyFactory, this.conversionStrategy.convert(base64PrivateKey));
        // Khởi tạo Signature với thuật toán Hash đã chọn
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
        // Khởi tạo secure Random
        this.secureRandom = SecureRandom.getInstance(DEFAULT_SECURE_RANDOM);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.key.getAlgorithm());
        keyPairGenerator.initialize(this.key.getSize(), secureRandom);
        this.keyPair = keyPairGenerator.generateKeyPair();
        // Khởi tạo Key Factory với thuật toán đã chọn
        this.keyFactory = KeyFactory.getInstance(this.key.getAlgorithm());
        // Khởi tạo Signature với thuật toán Hash đã chọn
        this.signature = Signature.getInstance(this.key.getHashFunction());
    }

    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(KeySignature key) throws CipherException {
        this.key = key;
    }

    /**
     * Khởi tạo khóa cho thuật toán bao gồm Public key và Private key
     */
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

    /**
     * Lấy khóa từ file
     *
     * @param src đường dẫn file chứa khóa
     * @throws IOException     lỗi đọc file
     * @throws CipherException lỗi chuyển đổi khóa
     */
    @Override
    public void loadKey(String src) throws IOException, CipherException {
        this.key = FileUtil.loadKeySignature(src);
    }

    /**
     * Lưu khóa xuống file
     * <p>Lưu cả Public key và Private key</p>
     * @param dest đường dẫn lưu file khóa
     * @throws IOException lỗi ghi file
     */
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

    /**
     * Thực hiện ký văn bản
     *
     * @param message văn bản cần ký
     * @return bản mã
     * @throws CipherException lỗi mã hóa
     */
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
