import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class concatenatorForProductTest {

    @Test
    void addline() {

        concatenatorForProduct actual = new concatenatorForProduct();
        String str = "2-6";

        StringBuilder expected = new StringBuilder("PRODUCT NOT FOUND\n");
        assertEquals(expected.toString(),actual.addline(str).toString());

    }
}