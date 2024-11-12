package nlu.fit.leanhduc.service.cipher;

import lombok.Getter;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class CipherSpecification {
    private Cipher algorithm;
    private Map<Mode, List<Padding>> validModePaddingCombinations;
    private Set<Integer> supportedKeySizes;  // Set to hold supported key sizes
    private Map<Mode, Integer> ivSizes;  // Map to hold IV sizes for each mode

    public static final CipherSpecification AES = new CipherSpecification(
            Cipher.AES,
            Map.of(
                    Mode.CBC, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.CFB, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.OFB, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)
            ),
            Set.of(128, 192, 256),  // Supported key sizes for AES
            Map.of(
                    Mode.CBC, 128,  // AES CBC mode requires 128-bit IV
                    Mode.CFB, 128,  // AES CFB mode requires 128-bit IV
                    Mode.OFB, 128,  // AES OFB mode requires 128-bit IV
                    Mode.CTR, 128  // AES CTR mode requires 128-bit IV
            )
    );

    public static final CipherSpecification DES = new CipherSpecification(
            Cipher.DES,
            Map.of(
                    Mode.CBC, List.of(Padding.PKCS5Padding),
                    Mode.ECB, List.of(Padding.PKCS5Padding)
            ),
            Set.of(56),  // Supported key size for DES (56-bit)
            Map.of(
                    Mode.CBC, 64,  // DES CBC mode requires 64-bit IV
                    Mode.ECB, 64   // DES ECB mode requires 64-bit IV
            )
    );

    public static final CipherSpecification TRIPLEDES = new CipherSpecification(
            Cipher.DESEDE,
            Map.of(
                    Mode.CBC, List.of(Padding.PKCS5Padding),
                    Mode.ECB, List.of(Padding.PKCS5Padding)
            ),
            Set.of(112, 168),  // Supported key sizes for DESede (112-bit, 168-bit)
            Map.of(
                    Mode.CBC, 64,  // DESede CBC mode requires 64-bit IV
                    Mode.ECB, 64   // DESede ECB mode requires 64-bit IV
            )
    );


    // RSA doesn't require IVs but supports different key sizes
    public static final CipherSpecification RSA = new CipherSpecification(
            Cipher.RSA,
            Map.of(
                    Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS1Padding)
            ),
            Set.of(512, 1024, 2048, 4096),  // RSA supports these key sizes
            Map.of()  // RSA does not require IVs
    );

    // Blowfish cipher specification
    public static final CipherSpecification Blowfish = new CipherSpecification(
            Cipher.BLOWFISH,
            Map.of(
                    Mode.CBC, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.ECB, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.CFB, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding)
            ),
            Set.of(128, 192, 256),  // Blowfish supports key sizes of 128-bit, 192-bit, and 256-bit
            Map.of(
                    Mode.CBC, 64,  // Blowfish CBC mode requires 64-bit IV
                    Mode.ECB, 64,  // Blowfish ECB mode requires 64-bit IV
                    Mode.CFB, 64   // Blowfish CFB mode requires 64-bit IV
            )
    );

    // RC2 cipher specification
    public static final CipherSpecification RC2 = new CipherSpecification(
            Cipher.RC2,
            Map.of(
                    Mode.CBC, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding),
                    Mode.ECB, List.of(Padding.PKCS5Padding, Padding.PKCS7Padding)
            ),
            Set.of(40, 64, 128),  // RC2 supports key sizes from 40 bits to 128 bits
            Map.of(
                    Mode.CBC, 64,  // RC2 CBC mode requires 64-bit IV
                    Mode.ECB, 64   // RC2 ECB mode requires 64-bit IV
            )
    );

    // RC4 cipher specification (stream cipher, no IV)
    public static final CipherSpecification RC4 = new CipherSpecification(
            Cipher.RC4,
            Map.of(
                    Mode.ECB, List.of(Padding.NoPadding)
            ),
            Set.of(40, 56, 128),  // RC4 supports key sizes of 40, 56, and 128 bits
            Map.of()  // RC4 does not require an IV
    );


    public CipherSpecification(Cipher algorithm, Map<Mode, List<Padding>> validModePaddingCombinations, Set<Integer> supportedKeySizes, Map<Mode, Integer> ivSizes) {
        this.algorithm = algorithm;
        this.validModePaddingCombinations = validModePaddingCombinations;
        this.supportedKeySizes = supportedKeySizes;
        this.ivSizes = ivSizes;
    }


}
