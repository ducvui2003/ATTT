package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESCipher extends AbsCipherNative {


    @Override
    public byte[] encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data.getBytes());
    }

    @Override
    public String decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(data);
        return new String(decryptedBytes);
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            BufferedInputStream bis = new BufferedInputStream((new FileInputStream(src)));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
            CipherInputStream cis = new CipherInputStream(bis, cipher);

// Read: doc tu file (input - read)
// Write: ghi vao file (output - write)
            byte[] read = new byte[1024];
            int bytesRead;
//            byte from bis -> bos
            while ((bytesRead = cis.read(read)) != -1) {
                bos.write(read, 0, bytesRead);
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
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new CipherException(e.getMessage());
        }
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
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
