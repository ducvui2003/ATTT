package nlu.fit.leanhduc.util.alphabet;


public interface AlphabetUtil {
    int getLength();

    char[] getAlphabet();

    int indexOf(char c);

    char getChar(int index);

    char getChar(int oct, boolean isLowerCase);
}
