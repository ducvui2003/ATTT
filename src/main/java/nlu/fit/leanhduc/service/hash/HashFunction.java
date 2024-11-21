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
import java.util.Set;
import java.util.TreeSet;

public class HashFunction extends AbsHash {
    Hash hash;

    public HashFunction() {
    }

    public HashFunction(Hash hash) {
        this.hash = hash;
    }

    @Override
    public String hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hash.getValue());
        byte[] data = message.getBytes();
        byte[] digest = md.digest(data);
        return byteConversionStrategy.convert(digest);
    }

    @Override
    public String hashWithHMAC(String message, String key) throws NoSuchAlgorithmException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), this.hash.getHmacValue());

        Mac mac = Mac.getInstance("Hmac" + hash.getValue());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return this.byteConversionStrategy.convert(hmacBytes);
    }

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

    public static void main(String[] args) {
        Set<String> messageDigests = new TreeSet<>();
        Set<String> macs = new TreeSet<>();

        for (String algorithm : Security.getAlgorithms("MessageDigest")) {
            messageDigests.add(algorithm);
        }

        for (String algorithm : Security.getAlgorithms("Mac")) {
            macs.add(algorithm);
        }

//        for (String algorithm : Security.getAlgorithms("Cipher")) {
//            System.out.println(algorithm);
//        }


        System.out.println("Supported Hash Functions (MessageDigests):");
        messageDigests.forEach(System.out::println);

        System.out.println("\nSupported HMAC Algorithms (Mac):");
        macs.forEach(System.out::println);
    }
}
