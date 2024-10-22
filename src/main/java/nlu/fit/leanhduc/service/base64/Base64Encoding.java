package nlu.fit.leanhduc.service.base64;

import java.util.Base64;

public class Base64Encoding {
    public String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decrypt(String input) {
        return new String(Base64.getDecoder().decode(input.toLowerCase()));
    }
}
