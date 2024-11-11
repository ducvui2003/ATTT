package nlu.fit.leanhduc.util.convert;

import java.util.Base64;

public class Base64ConversionStrategy implements ByteConversionStrategy {
    @Override
    public String convert(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    @Override
    public byte[] convert(String data) {
        return Base64.getDecoder().decode(data);
    }
}
