package nlu.fit.leanhduc.util.convert;

public class HexConversionStrategy implements ByteConversionStrategy {
    @Override
    public String convert(byte[] data) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }

    @Override
    public byte[] convert(String hexString) {
        int length = hexString.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Invalid Hex string. Length must be even.");
        }

        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            int byteValue = Integer.parseInt(hexString.substring(i, i + 2), 16);
            data[i / 2] = (byte) byteValue;
        }
        return data;
    }

}
