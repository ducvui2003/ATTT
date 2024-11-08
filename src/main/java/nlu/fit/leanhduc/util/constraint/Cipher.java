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
    ;
    private final String name;
    private final String displayName;

    Cipher(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
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
