package nlu.fit.leanhduc.util.alphabet;


import java.util.List;

/**
 * Interface {@code AlphabetUtil} định nghĩa các phương thức cơ bản của một bảng chữ cái
 * <p>Sử dụng cho các thuật toán cổ  điển cần xác định giá trị trên bảng chữ cái</p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
public interface AlphabetUtil {
    /**
     * @param isRandom {@code true} nếu muốn các ký tự sắp xếp ngẫu nhiên,
     *                 {@code false} nếu muốn tạo bảng chữ cái theo thứ tự
     * @return list chứa đầy đủ ký tự của bảng chữ cái
     */
    List<Character> generateAlphabet(boolean isRandom);

    /**
     * @return độ dài của bảng chữ cái
     */
    int getLength();

    /**
     * @return mảng ký tự của bảng chữ cái
     */
    char[] getAlphabet();

    /**
     * Tìm vị trí của ký tự của bảng chữ cái
     *
     * @param c ký tự cần tìm vị trí
     * @return vị trí của ký tự trong bảng chữ cái
     */
    int indexOf(char c);

    /**
     * Lấy ký tự tại vị trí index của bảng chữ cái
     *
     * @param index vị trí cần lấy ký tự
     * @return ký tự tại vị trí index
     */
    char getChar(int index);

    /**
     * Lấy ký tự tại vị trí index của bảng chữ cái
     *
     * @param index       giá trị index của ký tự
     * @param isLowerCase {@code true} nếu muốn ký tự in thường,
     *                    {@code false} nếu muốn ký tự in hoa
     * @return ký tự tại vị trí index
     */
    char getChar(int index, boolean isLowerCase);
}
