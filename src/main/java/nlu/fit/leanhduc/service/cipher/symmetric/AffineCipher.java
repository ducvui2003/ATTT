package nlu.fit.leanhduc.service.cipher.symmetric;

import lombok.Getter;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.AffineKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Getter
public class AffineCipher implements ISubstitutionCipher<AffineKey> {
    protected AffineKey key;
    protected Random rd;
    protected int range;
    protected AlphabetUtil alphabetUtil;

    public AffineCipher(AlphabetUtil alphabetUtil) {
        this.rd = new Random();
        this.alphabetUtil = alphabetUtil;
        this.range = this.alphabetUtil.getLength();
    }

    @Override
    public void loadKey(AffineKey key) throws CipherException {
        if (ModularUtil.findGCD(key.getA(), Constraint.ALPHABET_SIZE) != 1)
            throw new CipherException("Key is not a greatest common divisor");
        this.key = key;
    }

    @Override
    public AffineKey generateKey() {
        AffineKey key = new AffineKey();
        int a;
        do {
            a = rd.nextInt(1, this.range);
        } while (ModularUtil.findGCD(a, this.range) != 1);
        int b = rd.nextInt(1, this.range);
        key.setA(a);
        key.setB(b);
        return key;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        char[] plainTextArray = plainText.toCharArray();
        String result = "";
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Encrypt (x) = (a * x + b) mod m
                int index = this.alphabetUtil.indexOf(c);
                int charEncrypt = this.key.getA() * index + this.key.getB();
                result += alphabetUtil.getChar(charEncrypt, isLower);
            } else
                result += c;
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        char[] plainTextArray = encryptText.toCharArray();
        String result = "";
        int modularInverse = ModularUtil.modularInverse(this.key.getA(), this.range);
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Decrypt (x) = a^(-1) * (x - b)
                int index = this.alphabetUtil.indexOf(c);
                int charDecrypt = modularInverse * (index - this.key.getB());
                result += alphabetUtil.getChar(charDecrypt, isLower);
            } else
                result += c;
        }
        return result;
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        return false;
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return false;
    }

    @Override
    public boolean loadKey(String src) throws IOException {
        File file = new File(src);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            AffineKey key = (AffineKey) ois.readObject();
            this.loadKey(key);
            return true;
        } catch (EOFException | ClassNotFoundException | CipherException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveKey(String dest) throws IOException {
        File file = new File(dest);
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(file))) {
            ois.writeObject(key);
            return true;
        } catch (EOFException e) {
            return false;
        }
    }
}
