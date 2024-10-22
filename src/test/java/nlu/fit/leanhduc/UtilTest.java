package nlu.fit.leanhduc;


import nlu.fit.leanhduc.util.ModularUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {
    @Test
    public void test() {
        Assertions.assertEquals(ModularUtil.findGCD(56, 98), 14);
        Assertions.assertEquals(ModularUtil.findGCD(101, 10), 1);
    }
}
