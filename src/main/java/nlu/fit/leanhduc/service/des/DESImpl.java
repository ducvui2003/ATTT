package nlu.fit.leanhduc.service.des;

import javax.crypto.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DESImpl implements DES {
    SecretKey key;
    @Override
    public SecretKey genKey() {
        try {
            var generator = KeyGenerator.getInstance("DES");
            generator.init(56);
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    public boolean encryptFile(String srcf, String desf) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcf));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desf));
        CipherInputStream cis = new CipherInputStream(bis, cipher);

        byte[] read = new byte[1024];
        int i;
        while ((i = cis.read(read)) != -1) {
            bos.write(read, 0, i);
        }
        read = cipher.doFinal();
        if (read != null) {
            bos.write(read);
        }
        bos.flush();
        bos.close();
        cis.close();
        return true;
    }

    public boolean decryptFile(String srcf, String desf) throws Exception{
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcf));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desf));
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);

        byte[] read = new byte[1024];
        int i;
        while ((i = bis.read(read)) != -1) {
            bos.write(read, 0, i);
        }
        read = cipher.doFinal();
        if (read != null) {
            cos.write(read);
        }
        bos.flush();
        bos.close();
        cos.close();
        return true;
    }

    public static void main(String[] args) throws Exception {
        DESImpl des = new DESImpl();
        des.genKey();
        des.encryptFile("/home/nqat0919/Desktop/1.png", "/home/nqat0919/Desktop/encrypted.png");
        des.decryptFile("/home/nqat0919/Desktop/encrypted.png", "/home/nqat0919/Desktop/decrypted.png");
    }
}
