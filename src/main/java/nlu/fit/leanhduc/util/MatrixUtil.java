package nlu.fit.leanhduc.util;

/**
 * Class {@code MatrixUtil}
 * <p>Hiện thực các hàm hỗ trợ cho xử lý ma trận</p>
 */
public class MatrixUtil {
    /**
     * <p>Nhân hai ma trận</p>
     *
     * @param a ma trận thứ nhất
     * @param b ma trận thứ hai
     * @return ma trận sau khi cộng
     */
    public static int[] multiMatrix(int[] a, int[][] b) {
        int[] result = new int[a.length];
        if (a.length != b.length) return null;
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i] += a[j] * b[j][i];
            }
        }
        return result;
    }

    /**
     * <p>Nhân ma trận với một số thực</p>
     *
     * @param a số thực
     * @param b ma trận
     * @return ma trận sau khi nhân
     */
    public static int[][] multiMatrix(int a, int[][] b) {
        int[][] result = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i][j] = a * b[i][j];
            }
        }
        return result;
    }

    /**
     * <p>Nhân ma trận với một số thực</p>
     *
     * @param a số thực
     * @param b ma trận
     * @return ma trận sau khi nhân
     */
    public static int[][] multiMatrix(double a, int[][] b) {
        int[][] result = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i][j] = (int) (a * b[i][j]);
            }
        }
        return result;
    }

    /**
     * <p>Tính định thức ma trận</p>
     *
     * @param matrix ma trận
     * @return định thức của ma trận
     */
    public static int determinant(int[][] matrix) {
        int n = matrix.length;

        // Base case for 1x1 matrix
        if (n == 1) {
            return matrix[0][0];
        }

        // Base case for 2x2 matrix
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int det = 0; // Initialize determinant

        // Recursive case
        for (int i = 0; i < n; i++) {
            // Calculate the minor matrix
            int[][] minor = getChildMatrix(matrix, 0, i);
            // Add or subtract the determinant of the minor matrix
            det += (int) (Math.pow(-1, i) * matrix[0][i] * determinant(minor));
        }

        return det;
    }

    /**
     * <p>Lấy ra ma trận con của ma trận</p>
     *
     * @param matrix ma trận
     * @param row    dòng bị cắt
     * @param col    cột bị cắt
     * @return ma trận bị cắt đi row và col
     */
    private static int[][] getChildMatrix(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] minor = new int[n - 1][n - 1];
        int r = 0, c;

        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minor;
    }

    /**
     * <p>Lấy ra ma trận chuyển vị của ma trận</p>
     * <p>Thay đổi cột thành hàng, hàng thành cột</p>
     *
     * @param matrix ma trận
     * @return ma trận chuyển vị
     */
    public static int[][] getMatrixTranspose(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    /**
     * <p>Lấy ra ma trận phụ hợp của ma trận</p>
     *
     * @param matrix ma trận
     * @return ma trận chuyển vị
     */
    public static int[][] getMatrixAdjunct(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = getAlgebraicComplement(matrix, i, j);
            }
        }
        return result;
    }

    /**
     * <p>Lấy ra phần bù đại số</p>
     *
     * @param matrix ma trận cha có kích thước dòng và cột bé hơn row và col
     * @param row    dòng
     * @param col    cột
     * @return ma trận chuyển vị
     */
    public static int getAlgebraicComplement(int[][] matrix, int row, int col) {
        int[][] matrixM = getChildMatrix(matrix, row, col);
        return (int) (Math.pow(-1, row + col) * determinant(matrixM));
    }

}
