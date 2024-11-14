package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public abstract class AbsClassicCipher<T> implements ICipher<T> {
    protected T key;
    protected AlphabetUtil alphabetUtil;

    public AbsClassicCipher(AlphabetUtil alphabetUtil) {
        this.alphabetUtil = alphabetUtil;
    }

    @Override
    public void loadKey(T key) throws CipherException {
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
        } catch (CipherException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveKey(String dest) throws IOException {
        File file = new File(dest);
        if (!file.exists()) {
            createPathIfNotExists(dest);
        }
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(file))) {
            ois.writeObject(key);
            return true;
        } catch (EOFException e) {
            return false;
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
}
