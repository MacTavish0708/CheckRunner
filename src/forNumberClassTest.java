import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class forNumberClassTest {

    @Test
    void nSpace() {

        forNumberClass actual = new forNumberClass();
        int a=20;
        String b="aa";
        String c="aaaa";
        String d="aaaaaa";
        String e="aaa";
        String expected ="     ";
        assertEquals(expected,actual.nSpace(a,b,c,d,e));
    }
}