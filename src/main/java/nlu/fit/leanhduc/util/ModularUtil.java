package nlu.fit.leanhduc.util;

/**
 * Class {@code ModularUtil}
 * <p>
 * Hiện thực các hàm hỗ trợ cho xử lý số học module
 * </p>
 */
public class ModularUtil {
    /**
     * <p>Tìm ước chung lớn nhất của hai số</p>
     *
     * @param a số thứ nhất
     * @param b số thứ hai
     * @return ước chung lớn nhất của hai số
     */
    public static int findGCD(int a, int b) {
        int swap = a;
        a = Math.max(a, b);
        b = Math.min(swap, b);
        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    /**
     * <p>Tìm nghịch đảo modulo của một số</p>
     * <p> Sử dụng tiên đề Euclid</p>
     */
    private static int[] extendedGCD(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0};
        }

        int[] vals = extendedGCD(b, a % b);
        int gcd = vals[0];
        int x1 = vals[1];
        int y1 = vals[2];

        int x = y1;
        int y = x1 - (a / b) * y1;

        return new int[]{gcd, x, y};
    }

    /**
     * <p>Tìm nghịch đảo modulo của một số</p>
     * <p> Sử dụng tiên đề Euclid</p>
     *
     * @param a số cần tìm nghịch đảo
     * @param m số modulo
     * @return nghịch đảo modulo của a
     */
    public static int modularInverse(int a, int m) {
        int[] vals = extendedGCD(a, m);
        int gcd = vals[0];
        int x = vals[1];

        if (gcd != 1) {
            throw new IllegalArgumentException("Modular inverse does not exist for the given input.");
        }

        return (x % m + m) % m;
    }
}
