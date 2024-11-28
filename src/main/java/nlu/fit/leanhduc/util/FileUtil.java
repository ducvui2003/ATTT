package nlu.fit.leanhduc.util;

import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.service.key.KeySignature;
import nlu.fit.leanhduc.service.key.KeySymmetric;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Class {@code FileUtil}
 * <p>Hiện thực các hàm hỗ trợ cho xử lý file và lưu khóa vào file</p>
 */
public class FileUtil {

    /**
     * Tạo thư mục và file nếu chưa tồn tại
     *
     * @param filePath đường dẫn file
     * @throws IOException nếu không thể tạo thư mục hoặc file
     */
    public static void createPathIfNotExists(String filePath) throws IOException {
        Path file = Path.of(filePath);
        Path parentDir = file.getParent();

        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    /**
     * Lưu khóa của mã hóa đối xứng vào file
     *
     * @param key  khóa đối xứng cần lưu
     * @param dest đường dẫn file lưu khóa
     */
    public static void saveKey(KeySymmetric key, String dest) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dest))) {
//            Lưu chiều dài và byte của secretKey
            byte[] secretKeyBytes = key.getSecretKey().getEncoded();
            dos.writeInt(secretKeyBytes.length);
            dos.write(secretKeyBytes);
//            Lưu chiều dài và byte của iv
            byte[] ivBytes = key.getIv().getIV();
            dos.writeInt(key.getIv().getIV().length);
            dos.write(ivBytes);
//            Lưu tên thuật toán mã hóa
            dos.writeUTF(key.getCipher());
//            Lưu mode và padding
            dos.writeUTF(key.getMode());
            dos.writeUTF(key.getPadding());
//            Lưu kích thước của key và iv (đơn vị: bit)
            dos.writeInt(key.getKeySize());
            dos.writeInt(key.getIvSize());
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tải khóa mã hóa đối xứng từ file
     *
     * @param src đường dẫn file chứa khóa
     * @return khóa mã hóa đối xứng
     */
    public static KeySymmetric loadKey(String src) {
        KeySymmetric key = new KeySymmetric();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            byte[] secretKeyBytes = new byte[dis.readInt()];
            // Đọc secretKey từ file
            dis.readFully(secretKeyBytes);
            // Đọc iv từ file
            byte[] ivBytes = new byte[dis.readInt()];
            dis.readFully(ivBytes);
            // Chuyển đổi iv thành đối tượng IvParameterSpec
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            // Đọc thông tin thuật toán, mode, padding, kích thước từ file
            String algorithm = dis.readUTF();
            String mode = dis.readUTF();
            String padding = dis.readUTF();
            int keySize = dis.readInt();
            int ivSize = dis.readInt();
            // Chuyển đổi secretKey từ byte[] thành đối tượng SecretKey
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
            key.setSecretKey(secretKey);
            key.setIv(iv);
            key.setCipher(algorithm);
            key.setMode(mode);
            key.setPadding(padding);
            key.setKeySize(keySize);
            key.setIvSize(ivSize);
            return key;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lưu khóa của mã hóa bất đối xứng vào file
     *
     * @param key  khóa đối xứng cần lưu
     * @param dest đường dẫn file lưu khóa
     */
    public static void saveKey(KeyAsymmetric key, String dest) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dest))) {
            byte[] publicKeyBytes = key.getPublicKey().getEncoded();
            // Lưu chiều dài và byte của publicKey
            dos.writeInt(publicKeyBytes.length);
            dos.write(publicKeyBytes);
            byte[] privateKeyBytes = key.getPrivateKey().getEncoded();
            // Lưu chiều dài và byte của privateKey
            dos.writeInt(privateKeyBytes.length);
            dos.write(privateKeyBytes);
            // Lưu tên thuật toán, mode, padding, kích thước của key
            dos.writeUTF(key.getCipher());
            dos.writeUTF(key.getMode());
            dos.writeUTF(key.getPadding());
            dos.writeInt(key.getKeySize());
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tải khóa bất đối xứng từ file
     *
     * @param src đường dẫn file chứa khóa
     * @return khóa bất đối xứng
     */
    public static KeyAsymmetric loadKeyAsymmetric(String src) {
        KeyAsymmetric key = new KeyAsymmetric();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            // Đọc publicKey từ file
            byte[] publicKeyBytes = new byte[dis.readInt()];
            dis.readFully(publicKeyBytes);
            // Đọc privateKey từ file
            byte[] privateKeyBytes = new byte[dis.readInt()];
            dis.readFully(privateKeyBytes);
            // Đọc thông tin thuật toán, mode, padding, kích thước từ file
            String algorithm = dis.readUTF();
            String mode = dis.readUTF();
            String padding = dis.readUTF();
            int keySize = dis.readInt();
            //Khởi tạo KeyFactory để chuyển đổi byte[] thành PublicKey và PrivateKey
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            key.setPublicKey(keyFactory, publicKeyBytes);
            key.setPrivateKey(keyFactory, privateKeyBytes);
            key.setCipher(algorithm);
            key.setMode(mode);
            key.setPadding(padding);
            key.setKeySize(keySize);
            return key;
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lưu khóa của chữ ký điện tử vào file
     *
     * @param key  khóa đối xứng cần lưu
     * @param dest đường dẫn file lưu khóa
     */
    public static void saveKeySignature(KeySignature key, String dest) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dest))) {
            // Lưu publicKey xuống file
            byte[] publicKeyBytes = key.getPublicKey().getEncoded();
            dos.writeInt(publicKeyBytes.length);
            dos.write(publicKeyBytes);
            // Lưu privateKey xuống file
            byte[] privateKeyBytes = key.getPrivateKey().getEncoded();
            dos.writeInt(privateKeyBytes.length);
            dos.write(privateKeyBytes);
            // Lưu tên thuật toán, hashFunction, kích thước của key
            dos.writeUTF(key.getAlgorithm());
            dos.writeUTF(key.getHashFunction());
            dos.writeInt(key.getSize());
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tải khóa của chữ ký điện tử từ file
     *
     * @param src đường dẫn file chứa khóa
     * @return khóa bất đối xứng
     */
    public static KeySignature loadKeySignature(String src) {
        KeySignature key = new KeySignature();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            // Đọc publicKey từ file
            byte[] publicKeyBytes = new byte[dis.readInt()];
            dis.readFully(publicKeyBytes);
            // Đọc privateKey từ file
            byte[] privateKeyBytes = new byte[dis.readInt()];
            dis.readFully(privateKeyBytes);
            // Đọc thông tin thuật toán, hashFunction, kích thước từ file
            String algorithm = dis.readUTF();
            String hashFunction = dis.readUTF();
            int size = dis.readInt();
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            key.setPublicKey(keyFactory, publicKeyBytes);
            key.setPrivateKey(keyFactory, privateKeyBytes);
            key.setAlgorithm(algorithm);
            key.setHashFunction(hashFunction);
            key.setSize(size);
            return key;
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
