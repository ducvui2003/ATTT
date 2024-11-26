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

public class SymmetricCipherNative extends AbsCipherNative<KeySymmetric> {
    Cipher cipher;

    public SymmetricCipherNative() {

    }

    public SymmetricCipherNative(String base64SecretKey, String base64Iv, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeySymmetric();
        this.key.setSecretKey(new SecretKeySpec(
                conversionStrategy.convert(base64SecretKey), algorithm.getCipher()));
        if (base64Iv != null) {
            this.key.setIv(new IvParameterSpec(conversionStrategy.convert(base64Iv)));
        }
        this.key.setCipher(algorithm.getCipher());
        String provider = algorithm.getProvider() != null ? algorithm.getProvider() : Constraint.DEFAULT_PROVIDER;
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

    @Override
    public KeySymmetric generateKey() {
        KeySymmetric key = new KeySymmetric();
        try {
            String provider = algorithm.getProvider() != null ? algorithm.getProvider() : Constraint.DEFAULT_PROVIDER;
            KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm.cipher, provider);
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

    @Override
    public String encrypt(String plainText) throws CipherException {
        try {
            initCipher(Cipher.ENCRYPT_MODE);
            byte[] bytePlainText = plainText.getBytes(StandardCharsets.UTF_8);
            if (this.key.getPadding().equals("NoPadding"))
                bytePlainText = PaddingUtil.handleNoPaddingEncrypt(bytePlainText, this.algorithm.getBlockSize());
            byte[] byteEncrypt = cipher.doFinal(bytePlainText);
            return this.conversionStrategy.convert(byteEncrypt);
        } catch (Exception e) {
            throw new CipherException(e.getMessage());
        }
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        try {
            initCipher(Cipher.DECRYPT_MODE);
            byte[] bytesEncryptText = this.conversionStrategy.convert(encryptText);
            byte[] bytesAfterDecrypt = cipher.doFinal(bytesEncryptText);
            if (this.key.getPadding().equals("NoPadding"))
                bytesAfterDecrypt = PaddingUtil.handleNoPaddingDecrypt(bytesAfterDecrypt);
            return new String(bytesAfterDecrypt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CipherException(e.getMessage());
        }
    }

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
//            byte from bis -> bos
            while ((i = cis.read(read)) != -1) {
                bos.write(read, 0, i);
            }
//            cipher use bos to encrypt
            read = cipher.doFinal();
            if (read != null) {
                bos.write(read);
            }
            cis.close();
            bos.flush();
            bos.close();
            return true;
        } catch (InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException e) {
            throw new CipherException(e.getMessage());
        }
    }

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
            read = cipher.doFinal();
            if (read != null) {
                cos.write(read);
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

    private void initCipher(int mode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.key.getIv() == null)
            cipher.init(mode, this.key.getSecretKey());
        else
            cipher.init(mode, this.key.getSecretKey(), this.key.getIv());
    }
}
