package nlu.fit.leanhduc.service.hash;

import nlu.fit.leanhduc.util.constraint.Hash;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * Class {@code HashFunction}
 * <p>
 * Class đại diện cho các thuật toán băm
 * </p>
 * <p>
 * Cung cấp các phương thức băm thông điệp, file và băm kèm HMAC
 * </p>
 */
public class HashFunction extends AbsHash {
    Hash hash;

    public HashFunction(Hash hash) {
        this.hash = hash;
    }

    /**
     * Băm văn bản
     *
     * @param message thông điệp cần băm
     * @return chuỗi băm
     * @throws NoSuchAlgorithmException nếu thuật toán băm không tồn tại
     */
    @Override
    public String hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hash.getValue());
        byte[] data = message.getBytes();
        byte[] digest = md.digest(data);
        return byteConversionStrategy.convert(digest);
    }

    /**
     * Băm văn bản kèm HMAC
     *
     * @param message thông điệp cần băm
     * @param key     khóa băm
     * @return chuỗi băm
     * @throws NoSuchAlgorithmException nếu thuật toán băm không tồn tại
     */
    @Override
    public String hashWithHMAC(String message, String key) throws NoSuchAlgorithmException {
//       Khởi tạo MAC với thuật toán băm và khóa
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), this.hash.getHmacValue());
        Mac mac = Mac.getInstance(this.hash.getHmacValue());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
//Tiến hanh băm văn bản
        byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return this.byteConversionStrategy.convert(hmacBytes);
    }

    /**
     * Băm file
     *
     * @param src đường dẫn file cần băm
     * @return chuỗi băm
     * @throws NoSuchAlgorithmException nếu thuật toán băm không tồn tại
     * @throws IOException              nếu không thể đọc file
     */
    @Override
    public String hashFile(String src) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(hash.getValue());
        File f = new File(src);
        if (!f.exists()) return null;

        DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(src)), md);

        byte[] buff = new byte[1024];
        int read;
        do {
            read = dis.read(buff);
        } while (read != -1);

        byte[] digest = dis.getMessageDigest().digest();
        dis.close();
        return this.byteConversionStrategy.convert(digest);
    }

    /**
     * Băm file kèm HMAC
     *
     * @param src đường dẫn file cần băm
     * @param key khóa băm
     * @return chuỗi băm
     * @throws NoSuchAlgorithmException nếu thuật toán băm không tồn tại
     * @throws IOException              nếu không thể đọc file
     */
    @Override
    public String hashFileWithHMAC(String src, String key) throws NoSuchAlgorithmException, IOException {
        File f = new File(src);
        if (!f.exists()) return null;
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), this.hash.getHmacValue());

        Mac mac = Mac.getInstance(this.hash.getHmacValue());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream fileInputStream = new FileInputStream(src)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                mac.update(buffer, 0, bytesRead);
            }
        }
        byte[] hmacBytes = mac.doFinal();
        return this.byteConversionStrategy.convert(hmacBytes);
    }
}
