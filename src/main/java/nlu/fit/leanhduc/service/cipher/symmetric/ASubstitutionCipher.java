package nlu.fit.leanhduc.service.cipher.symmetric;

import nlu.fit.leanhduc.service.ISubstitutionCipher;

import java.io.*;

public abstract class ASubstitutionCipher<T> implements ISubstitutionCipher<T> {
    T key;


    @Override
    public void loadKey(T key) {
        this.key = key;
    }

    @Override
    public boolean loadKey(String src) throws IOException {
        File file = new File(src);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            T key = (T) ois.readObject();
            this.loadKey(key);
            return true;
        } catch (EOFException | ClassNotFoundException e) {
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
