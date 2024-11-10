package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SymmetricCipherNative extends AbsCipherNative {
    SecretKey secretKey;
    Cipher cipher;

    public SymmetricCipherNative(Algorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(algorithm);
        this.cipher = Cipher.getInstance(algorithm.toString());
    }

    @Override
    public void loadKey(SecretKey secretKey) throws CipherException {
        this.secretKey = secretKey;
    }

    @Override
    public SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm.toString());
            keyGenerator.init(algorithm.getKeySize());
            secretKey = keyGenerator.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] encrypt(String data) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(byte[] data) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
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
        } catch (InvalidKeyException | IOException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new CipherException(e.getMessage());
        }
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
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
}
