package nlu.fit.leanhduc.util.convert;

public class ByteConverter {
    private ByteConversionStrategy strategy;

    public ByteConverter(ByteConversionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ByteConversionStrategy strategy) {
        this.strategy = strategy;
    }

    public String convert(byte[] data) {
        return strategy.convert(data);
    }

    public byte[] convert(String data) {
        return strategy.convert(data);
    }
}
