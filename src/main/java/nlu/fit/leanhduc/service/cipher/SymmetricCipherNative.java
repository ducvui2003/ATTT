package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.FileUtil;
import nlu.fit.leanhduc.util.PaddingUtil;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * Class {@code SymmetricCipherNative}
 * <p>Class đại diện cho các thuật toán mã hóa đối xứng java hỗ trợ</p>
 * <p>Cung cấp các phương thức mã hóa, giải mã và tạo khóa cho thuật toán mã hóa đối xứng</p>
 */
public class SymmetricCipherNative extends AbsCipherNative<KeySymmetric> {
    Cipher cipher;

    public SymmetricCipherNative() {

    }


    public SymmetricCipherNative(String base64SecretKey, String base64Iv, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeySymmetric();
        // Chuyển base64 của SecretKey về object
        this.key.setSecretKey(new SecretKeySpec(
                conversionStrategy.convert(base64SecretKey), algorithm.getCipher()));
        if (base64Iv != null) {
            // Chuyển base64 của IV về object
            this.key.setIv(new IvParameterSpec(conversionStrategy.convert(base64Iv)));
        }
        this.key.setCipher(algorithm.getCipher());
        String provider = algorithm.getProvider() != null ? algorithm.getProvider() : Constraint.DEFAULT_PROVIDER;
        // Khởi tạo cipher với thuật toán và provider
        this.cipher = Cipher.getInstance(algorithm.toString(), provider);
        this.key.setMode(algorithm.getMode());
        this.key.setPadding(algorithm.getPadding());
        this.key.setKeySize(algorithm.getKeySize());
        this.key.setIvSize(algorithm.getIvSize());
    }

    public SymmetricCipherNative(Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeySymmetric();
        String provider = algorithm.getProvider() != null ? algorithm.getProvider() : Constraint.DEFAULT_PROVIDER;
        this.cipher = Cipher.getInstance(algorithm.toString(), provider);
        if (algorithm.getIvSize() != 0) {
            this.key.setIv(generateIV());
        }
    }

    /**
     * Tạo một vector khởi tạo (IV) ngẫu nhiên
     *
     * @return vector khởi tạo
     */
    public IvParameterSpec generateIV() {
        byte[] iv = new byte[algorithm.getIvSize() / 8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    @Override
    public void loadKey(KeySymmetric key) throws CipherException {
        this.key = key;
    }

    /**
     * Tạo khóa cho thuật toán mã hóa đối xứng
     *
     * @return khóa
     */
    @Override
    public KeySymmetric generateKey() {
        KeySymmetric key = new KeySymmetric();
        try {
            String provider = algorithm.getProvider() != null ? algorithm.getProvider() : Constraint.DEFAULT_PROVIDER;
            KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm.getCipher(), provider);
            keyGenerator.init(algorithm.getKeySize());
            SecretKey secretKey = keyGenerator.generateKey();
            IvParameterSpec iv = generateIV();
            key.setSecretKey(secretKey);
            key.setIv(iv);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thực hiện mã hóa văn bản
     *
     * @param plainText văn bản cần mã hóa
     * @return văn bản đã mã hóa
     */
    @Override
    public String encrypt(String plainText) throws CipherException {
        try {
            // Khởi tạo cipher với chế độ mã hóa
            initCipher(Cipher.ENCRYPT_MODE);
            byte[] bytePlainText = plainText.getBytes(StandardCharsets.UTF_8);
            // Nếu không có padding thì thực hiện padding nếu chuỗi không đủ block size
            if (this.key.getPadding().equals("NoPadding"))
                bytePlainText = PaddingUtil.handleNoPaddingEncrypt(bytePlainText, this.algorithm.getBlockSize());
            byte[] byteEncrypt = cipher.doFinal(bytePlainText);
            return this.conversionStrategy.convert(byteEncrypt);
        } catch (Exception e) {
            throw new CipherException(e.getMessage());
        }
    }

    /**
     * Thực hiện giải mã văn bản
     *
     * @param encryptText văn bản cần giải mã
     * @return văn bản đã giải mã
     */
    @Override
    public String decrypt(String encryptText) throws CipherException {
        try {
            // Khởi tạo cipher với chế độ giải mã
            initCipher(Cipher.DECRYPT_MODE);
            byte[] bytesEncryptText = this.conversionStrategy.convert(encryptText);
            byte[] bytesAfterDecrypt = cipher.doFinal(bytesEncryptText);
            // Xóa padding đã đệm vào trước đó
            if (this.key.getPadding().equals("NoPadding"))
                bytesAfterDecrypt = PaddingUtil.handleNoPaddingDecrypt(bytesAfterDecrypt);
            return new String(bytesAfterDecrypt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CipherException(e.getMessage());
        }
    }

    /**
     * Thực hiện mã hóa file
     *
     * @param src  đường dẫn đến file cần mã hóa
     * @param dest đường dẫn lưu file sau khi mã hóa
     * @return {@code true} nếu mã hóa thành công, ngược lại {@code false}
     * @throws CipherException nếu có lỗi trong quá trình mã hóa
     */
    @Override
    public boolean encrypt(String src, String dest) throws CipherException {
        try {
            initCipher(Cipher.ENCRYPT_MODE);
            BufferedInputStream bis = new BufferedInputStream((new FileInputStream(src)));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
            CipherInputStream cis = new CipherInputStream(bis, cipher);

            // Read: doc tu file (input - read)
            // Write: ghi vao file (output - write)
            byte[] read = new byte[1024];
            int i;
            //  byte from bis -> bos
            while ((i = cis.read(read)) != -1) {
                bos.write(read, 0, i);
            }

            cis.close();
            bos.flush();
            bos.close();
            return true;
        } catch (InvalidKeyException | IOException |
                 InvalidAlgorithmParameterException e) {
            throw new CipherException(e.getMessage());
        }
    }

    /**
     * Thực hiện giải mã file
     *
     * @param src  đường dẫn đến file cần giải mã
     * @param dest đường dẫn lưu file sau khi giải mã
     * @return {@code true} nếu giải mã thành công, ngược lại {@code false}
     * @throws CipherException nếu có lỗi trong quá trình giải mã
     */
    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        try {
            initCipher(Cipher.DECRYPT_MODE);
            BufferedInputStream bis = new BufferedInputStream((new FileInputStream(src)));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
            CipherOutputStream cos = new CipherOutputStream(bos, cipher);

            byte[] read = new byte[1024];
            int i;
            while ((i = bis.read(read)) != -1) {
                cos.write(read, 0, i);
            }
            bis.close();
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            throw new CipherException(e.getMessage());
        }
    }

    @Override
    public void loadKey(String src) throws IOException, CipherException {
        this.key = FileUtil.loadKey(src);
    }

    @Override
    public void saveKey(String dest) throws CipherException {
        FileUtil.saveKey(this.key, dest);
    }

    /**
     * Khởi tạo cipher với chế độ mã hóa
     *
     * @param mode chế độ mã hóa
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    private void initCipher(int mode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.key.getIv() == null)
            cipher.init(mode, this.key.getSecretKey());
        else
            cipher.init(mode, this.key.getSecretKey(), this.key.getIv());
    }
}
