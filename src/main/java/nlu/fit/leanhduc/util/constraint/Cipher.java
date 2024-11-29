package nlu.fit.leanhduc.util.constraint;

/**
 * Enum {@code Cipher}
 * <p>
 * Enum định nghĩa các thuật toán mã hóa hỗ trợ
 * </p>
 */
public enum Cipher {
    SHIFT("Shift", "Dịch chuyển"),
    SUBSTITUTION("Substitution", "Thay thế"),
    AFFINE("Affine", "Affine"),
    VIGINERE("Vigenere", "Vigenere"),
    HILL("Hill", "Hill"),
    AES("AES", "AES", 16),
    DES("DES", "DES", 8),
    BLOWFISH("Blowfish", "Blowfish", 8),
    RSA("RSA", "RSA"),
    DESEDE("DESede", "DESede", 8),
    RC2("RC2", "RC2", 8),
    RC4("RC4", "RC4", "BC"),
    CAMELLIA("Camellia", "Camellia", 16, "BC"),
    TWOFISH("Twofish", "Twofish", 16, "BC"),
    IDEA("IDEA", "IDEA", 8, "BC"),
    DSA("DSA", "DSA", "BC");
    private final String name;
    private final String displayName;
    private int blockSize;// đơn vị: byte
    private String provider;

    Cipher(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    Cipher(String name, String displayName, String provider) {
        this.name = name;
        this.displayName = displayName;
        this.provider = provider;
    }

    Cipher(String name, String displayName, int blockSize) {
        this.name = name;
        this.displayName = displayName;
        this.blockSize = blockSize;
    }

    Cipher(String name, String displayName, int blockSize, String provider) {
        this.name = name;
        this.displayName = displayName;
        this.blockSize = blockSize;
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getBlockSize() {
        return blockSize;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
