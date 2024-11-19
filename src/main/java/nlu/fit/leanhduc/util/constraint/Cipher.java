package nlu.fit.leanhduc.util.constraint;

public enum Cipher {
    SHIFT("Shift", "Dịch chuyển"),
    SUBSTITUTION("Substitution", "Thay thế"),
    AFFINE("Affine", "Affine"),
    VIGENERE("Vigenere", "Vigenere"),
    HILL("Hill", "Hill"),
    AES("AES", "AES"),
    DES("DES", "DES"),
    BLOWFISH("Blowfish", "Blowfish"),
    RSA("RSA", "RSA"),
    DESEDE("DESede", "DESede"),
    RC2("RC2", "RC2"),
    RC4("RC4", "RC4"),
    CAMELLIA("Camellia", "Camellia"),
    TWOFISH("Twofish", "Twofish"),
    IDEA("IDEA", "IDEA");
    private final String name;
    private final String displayName;
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

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
