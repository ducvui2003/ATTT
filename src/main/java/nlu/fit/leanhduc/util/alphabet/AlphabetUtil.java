package nlu.fit.leanhduc.util.alphabet;


import java.util.List;

public interface AlphabetUtil {

    List<Character> generateAlphabet(boolean isRandom);

    int getLength();

    char[] getAlphabet();

    int indexOf(char c);

    char getChar(int index);

    char getChar(int index, boolean isLowerCase);
}
