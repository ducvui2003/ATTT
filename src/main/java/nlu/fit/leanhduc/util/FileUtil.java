package nlu.fit.leanhduc.util;

import nlu.fit.leanhduc.service.key.KeySymmetric;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
//    public static void writeStringToFile(String path, String content) throws IOException {
//        Files.write(Paths.get(path), content.getBytes());
//    }
//
//    public static void saveKeyToFile(SecretKey key, String fileName) throws IOException {
//        FileOutputStream fileOut = new FileOutputStream(fileName);
//        DataOutputStream dataOut = new DataOutputStream(fileOut);
//
//        byte[] keyBytes = key.getEncoded();
//        dataOut.writeInt(keyBytes.length);
//        dataOut.write(keyBytes);
//
//        System.out.println("SecretKey saved to " + fileName);
//    }

    public static SecretKey loadKeyFromFile(String fileName, String algorithm) {
        try (FileInputStream fileIn = new FileInputStream(fileName);
             DataInputStream dataIn = new DataInputStream(fileIn)) {

            int length = dataIn.readInt();              // Read the length of the byte array
            byte[] keyBytes = new byte[length];
            dataIn.readFully(keyBytes);                 // Read the byte array

            return new SecretKeySpec(keyBytes, algorithm);  // Recreate the SecretKey object

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createPathIfNotExists(String filePath) throws IOException {
        Path file = Path.of(filePath);
        Path parentDir = file.getParent();

        // Check if the parent directory exists; if not, create it
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
}
