package nlu.fit.leanhduc.service.cipher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;

import java.util.*;

/**
 * Class {@code CipherSpecification}  chứa thông tin về thuật toán mã hóa.
 * <p>Nó bao gồm tên thuật toán, chế độ hoạt động (mode) và cách đệm (padding).</p>
 * <p>Lớp này hỗ trợ tạo chuỗi đặc tả mã hóa hoàn chỉnh theo định dạng chuẩn.</p>
 * <p>Các thuật toán sẽ được định nghĩa trước và được lấy thông qua {@link #findCipherSpecification }</p>
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CipherSpecification {
    /*
     * Tên thuật toán
     */
    Cipher algorithm;
    /*
     * Mode và danh sách padding đi kèm
     */
    Map<Mode, List<Padding>> validModePaddingCombinations;
    /*
     * Kích thước key hỗ trợ
     */
    Set<Size> supportedKeySizes;
    /*
     * Kích thước iv (vecto khởi đầu) theo mode
     */
    Map<Mode, Size> ivSizes;

    /**
     * Tìm thông số mã hóa tương ứng với thuật toán mã hóa
     *
     * @param cipher thuật toán mã hóa
     * @return thông số mã hóa tương ứng
     */
    public static final CipherSpecification findCipherSpecification(Cipher cipher) {
        CipherSpecification result = switch (cipher) {
            case AES -> AES;
            case DES -> DES;
            case DESEDE -> TRIPLEDES;
            case RSA -> RSA;
            case BLOWFISH -> Blowfish;
            case RC2 -> RC2;
            case RC4 -> RC4;
            case TWOFISH -> TWOFISH;
            case CAMELLIA -> CAMELLIA;
            case IDEA -> IDEA;
            default -> null;
        };
        if (result != null) {
            result.supportedKeySizes = new TreeSet<>(result.supportedKeySizes);
            result.ivSizes = new TreeMap<>(result.ivSizes);
            Map<Mode, List<Padding>> sortedValidModePaddingCombinations = new TreeMap<>(new Comparator<Mode>() {
                @Override
                public int compare(Mode o1, Mode o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            sortedValidModePaddingCombinations.putAll(result.validModePaddingCombinations);
            result.validModePaddingCombinations = sortedValidModePaddingCombinations;
        }
        return result;
    }

    private static final CipherSpecification AES = new CipherSpecification(
            Cipher.AES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.PCBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.CFB8, Size.Size_16),
                    Map.entry(Mode.CFB16, Size.Size_16),
                    Map.entry(Mode.CFB48, Size.Size_16),
                    Map.entry(Mode.CFB64, Size.Size_16),
                    Map.entry(Mode.CFB128, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.OFB8, Size.Size_16),
                    Map.entry(Mode.OFB16, Size.Size_16),
                    Map.entry(Mode.OFB48, Size.Size_16),
                    Map.entry(Mode.OFB64, Size.Size_16),
                    Map.entry(Mode.OFB128, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16),
                    Map.entry(Mode.CTS, Size.Size_16)
            )
    );

    private static final CipherSpecification DES = new CipherSpecification(
            Cipher.DES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_7),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
            )
    );

    private static final CipherSpecification TRIPLEDES = new CipherSpecification(
            Cipher.DESEDE,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_14, Size.Size_21),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
            )
    );


    private static final CipherSpecification RSA = new CipherSpecification(
            Cipher.RSA,
            Map.of(
                    Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS1Padding, Padding.OAEPPadding, Padding.OAEPWithMD5AndMGF1Padding)
            ),
            Set.of(Size.Size_64, Size.Size_128, Size.Size_256, Size.Size_512),
            Map.of()
    );

    private static final CipherSpecification Blowfish = new CipherSpecification(
            Cipher.BLOWFISH,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding))),
            Set.of(Size.Size_4, Size.Size_12, Size.Size_24, Size.Size_32, Size.Size_56),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
            )

    );

    
    private static final CipherSpecification RC2 = new CipherSpecification(
            Cipher.RC2,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_5, Size.Size_8, Size.Size_16),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
            )
    );

    private static final CipherSpecification RC4 = new CipherSpecification(
            Cipher.RC4,
            Map.of(
                    Mode.NONE, List.of(Padding.NoPadding),
                    Mode.ECB, List.of(Padding.NoPadding)
            ),
            Set.of(Size.Size_5, Size.Size_7, Size.Size_16),
            Map.of()
    );

    private static final CipherSpecification TWOFISH = new CipherSpecification(
            Cipher.TWOFISH,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding))
            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16)
            )
    );

    private static final CipherSpecification CAMELLIA = new CipherSpecification(
            Cipher.CAMELLIA,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16)
            )
    );

    private static final CipherSpecification IDEA = new CipherSpecification(
            Cipher.IDEA,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding))
            ),
            Set.of(Size.Size_16),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16)
            )
    );


    public CipherSpecification(Cipher algorithm, Map<Mode, List<Padding>> validModePaddingCombinations, Set<Size> supportedKeySizes, Map<Mode, Size> ivSizes) {
        this.algorithm = algorithm;
        this.validModePaddingCombinations = validModePaddingCombinations;
        this.supportedKeySizes = supportedKeySizes;
        this.ivSizes = ivSizes;
    }
}
