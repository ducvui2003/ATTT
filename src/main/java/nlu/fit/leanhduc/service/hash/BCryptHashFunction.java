package nlu.fit.leanhduc.service.hash;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class BCryptHashFunction extends AbsHash {
    int cost;

    public BCryptHashFunction(int cost) {
        this.cost = cost;
    }

    @Override
    public String hash(String message) throws NoSuchAlgorithmException {
        byte[] hash = BCrypt.withDefaults().hash(14, message.toCharArray());
        return byteConversionStrategy.convert(hash);
    }

    @Override
    public String hashWithHMAC(String message, String key) throws NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String hashFile(String src) throws NoSuchAlgorithmException, IOException {
        return "";
    }

    @Override
    public String hashFileWithHMAC(String src, String key) throws NoSuchAlgorithmException, IOException {
        return "";
    }
}
