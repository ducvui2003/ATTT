package nlu.fit.leanhduc.service.cipher;

import lombok.Setter;
import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.FileUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * Class {@code AsymmetricCipherNative}
 * <p>Class đại diện cho các thuật toán mã hóa bất đối xứng java hỗ trợ</p>
 * <p>Mặc định sử dụng thuật toán RSA</p>
 * <p>Cung cấp các phương thức mã hóa, giải mã và tạo khóa cho thuật toán mã hóa bất đối xứng</p>
 */
public class AsymmetricCipherNative extends AbsCipherNative<KeyAsymmetric> {
    KeyPair keyPair;
    Cipher cipher;

    // Mặc định sử dụng khóa công khai để mã hóa
    @Setter
    boolean encryptByPublicKey = true;

    public AsymmetricCipherNative() {
    }

    public AsymmetricCipherNative(Algorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        super(algorithm);
        this.key = new KeyAsymmetric();
        this.cipher = Cipher.getInstance(algorithm.toString(), Constraint.DEFAULT_PROVIDER);
    }

    public AsymmetricCipherNative(String base64PublicKey, String base64PrivateKey, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeyAsymmetric();
        KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm.getCipher());
        // Khởi tạo cipher với thuật toán và provider
        this.cipher = Cipher.getInstance(this.algorithm.toString(), Constraint.DEFAULT_PROVIDER);
        this.key.setCipher(algorithm.getCipher());
        // Chuyển base64 của PublicKey và PrivateKey về object
        this.key.setPublicKey(keyFactory, base64PublicKey);
        this.key.setPrivateKey(keyFactory, base64PrivateKey);
        this.key.setMode(algorithm.getMode());
        this.key.setPadding(algorithm.getPadding());
        this.key.setKeySize(algorithm.getKeySize());
    }


    @Override
    public void loadKey(KeyAsymmetric key) throws CipherException {
        this.key = key;
    }

    /**
     * Tạo khóa cho thuật toán
     *
     * @return khóa mới tạo
     */
    @Override
    public KeyAsymmetric generateKey() {
        KeyAsymmetric key = new KeyAsymmetric();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm.getCipher());
            keyPairGenerator.initialize(algorithm.getKeySize());
            keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            key.setPrivateKey(privateKey);
            key.setPublicKey(publicKey);
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tải khóa từ file
     *
     * @param src đường dẫn file chứa khóa
     */
    @Override
    public void loadKey(String src) throws IOException {
        this.key = FileUtil.loadKeyAsymmetric(src);
    }

    /**
     * Lưu khóa xuống file chỉ định
     *
     * @param dest đường dẫn lưu file khóa
     */
    @Override
    public void saveKey(String dest) throws IOException {
        FileUtil.saveKey(key, dest);
    }

    /**
     * Mã hóa văn bản bằng thuật toán bất đối xứng
     *
     * @param plainText bản rõ
     * @return bản mã
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        try {
            byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
            initCipherEncrypt();
            return conversionStrategy.convert(cipher.doFinal(bytes));
        } catch (IllegalBlockSizeException |
                 BadPaddingException ignored) {
            throw new RuntimeException(ignored);
        }
    }

    /**
     * Giải mã văn bản bằng thuật toán bất đối xứng
     *
     * @param encryptText bản mã
     * @return bản rõ
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptText);
            initCipherDecrypt();
            return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException {
        return super.encrypt(src, dest);
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return super.decrypt(src, dest);
    }

    /**
     * Khởi tạo cipher cho việc mã hóa
     */
    private void initCipherEncrypt() {
        try {
            if (encryptByPublicKey) {
                cipher.init(Cipher.ENCRYPT_MODE, key.getPublicKey());
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key.getPrivateKey());
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Khởi tạo cipher cho việc giải mã
     */
    private void initCipherDecrypt() {
        try {
            if (encryptByPublicKey) {
                cipher.init(Cipher.DECRYPT_MODE, key.getPrivateKey());
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key.getPublicKey());
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
