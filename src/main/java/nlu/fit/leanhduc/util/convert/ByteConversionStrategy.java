package nlu.fit.leanhduc.util.convert;

public interface ByteConversionStrategy {
    String convert(byte[] data);

    byte[] convert(String data);
}
