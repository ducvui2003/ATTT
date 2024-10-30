package nlu.fit.leanhduc.util;


public class MatrixUtil {
    public static int[] multiMatrix(int[] a, int[][] b) {
        int[] result = new int[a.length];
        if (a.length != b.length) return null;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i] += a[i] * b[j][j];
            }
        }
        return result;
    }
}
