package nlu.fit.leanhduc.util.alphabet;

import nlu.fit.leanhduc.util.Constraint;

import java.util.*;
/**
 * Class {@code VietnameseAlphabetUtil} implement {@code AlphabetUtil} cho bảng chữ cái Tiếng Việt
 * <p>Trong đó:
 * <p>{@link Constraint#FIRST_CHAR } đại điện cho ký tự đầu tiến </p>
 * <p>{@link Constraint#LAST_CHAR } đại điện cho ký tự cuối cùng </p>
 * <p>{@link Constraint#FIRST_CHAR_UPPER } đại điện cho ký tự đầu tiên in hoa </p>
 * <p>{@link Constraint#LAST_CHAR_UPPER } đại điện cho ký tự cuối cùng in hoa </p>
 * <p>{@link Constraint#ALPHABET_ARRAY } đại điện cho chuỗi chứa toàn bộ ký tự trong bảng chữ cái </p>
 * <p>{@link Constraint#ALPHABET_SIZE } đại điện cho số lượng ký tự trong bảng chữ cái </p>
 * </p>
 *
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
public class VietnameseAlphabetUtil implements AlphabetUtil {
    @Override
    public int indexOf(char c) {
        return Constraint.VIET_NAM_N.indexOf(c);
    }

    @Override
    public char getChar(int index) {
        return Constraint.VIET_NAM_N.charAt(index);
    }

    @Override
    public char getChar(int oct, boolean isLowerCase) {
        int index = ((oct % Constraint.VIET_NAME_ALPHA_BET_SIZE) + Constraint.VIET_NAME_ALPHA_BET_SIZE) % Constraint.VIET_NAME_ALPHA_BET_SIZE;

        return isLowerCase ?
                Constraint.VIET_NAME_ALPHA_ARRAY[index] :
                Character.toUpperCase(Constraint.VIET_NAME_ALPHA_ARRAY[index]);
    }


    @Override
    public List<Character> generateAlphabet(boolean isRandom) {
        List<Character> alphabet = new ArrayList<>();
        for (char c : Constraint.VIET_NAME_ALPHA_ARRAY) {
            alphabet.add(c);
        }
        for (char c : Constraint.VIET_NAME_ALPHA_ARRAY) {
            alphabet.add(Character.toUpperCase(c));
        }
        if (isRandom) {
            Collections.shuffle(alphabet);
        }
        return alphabet;
    }

    @Override
    public int getLength() {
        return Constraint.VIET_NAME_ALPHA_BET_SIZE;
    }

    @Override
    public char[] getAlphabet() {
        return Constraint.VIET_NAME_ALPHA_ARRAY;
    }
}
