package nlu.fit.leanhduc.util;

public class ModularUtil {
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
