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

public class FileUtil {


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

    public static KeySymmetric loadKey(String src) {
        KeySymmetric key = new KeySymmetric();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            byte[] secretKeyBytes = new byte[dis.readInt()];
            dis.readFully(secretKeyBytes);
            byte[] ivBytes = new byte[dis.readInt()];
            dis.readFully(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            String algorithm = dis.readUTF();
            String mode = dis.readUTF();
            String padding = dis.readUTF();
            int keySize = dis.readInt();
            int ivSize = dis.readInt();
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

    public static void saveKey(KeyAsymmetric key, String dest) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dest))) {
            byte[] publicKeyBytes = key.getPublicKey().getEncoded();
            dos.writeInt(publicKeyBytes.length);
            dos.write(publicKeyBytes);
            byte[] privateKeyBytes = key.getPrivateKey().getEncoded();
            dos.writeInt(privateKeyBytes.length);
            dos.write(privateKeyBytes);
            dos.writeUTF(key.getCipher());
            dos.writeUTF(key.getMode());
            dos.writeUTF(key.getPadding());
            dos.writeInt(key.getKeySize());
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyAsymmetric loadKeyAsymmetric(String src) {
        KeyAsymmetric key = new KeyAsymmetric();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            byte[] publicKeyBytes = new byte[dis.readInt()];
            dis.readFully(publicKeyBytes);
            byte[] privateKeyBytes = new byte[dis.readInt()];
            dis.readFully(privateKeyBytes);
            String algorithm = dis.readUTF();
            String mode = dis.readUTF();
            String padding = dis.readUTF();
            int keySize = dis.readInt();
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

    public static void saveKeySignature(KeySignature key, String dest) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dest))) {
            byte[] publicKeyBytes = key.getPublicKey().getEncoded();
            dos.writeInt(publicKeyBytes.length);
            dos.write(publicKeyBytes);
            byte[] privateKeyBytes = key.getPrivateKey().getEncoded();
            dos.writeInt(privateKeyBytes.length);
            dos.write(privateKeyBytes);
            dos.writeUTF(key.getAlgorithm());
            dos.writeUTF(key.getHashFunction());
            dos.writeInt(key.getSize());
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeySignature loadKeySignature(String src) {
        KeySignature key = new KeySignature();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(src))) {
            byte[] publicKeyBytes = new byte[dis.readInt()];
            dis.readFully(publicKeyBytes);
            byte[] privateKeyBytes = new byte[dis.readInt()];
            dis.readFully(privateKeyBytes);
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
