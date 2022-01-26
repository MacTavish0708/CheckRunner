import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class concatenatorForDiscountCardTest {

    @Test
    void addline() {

        concatenatorForDiscountCard actual = new concatenatorForDiscountCard();
        String str = "card-0000";

        StringBuilder expected = new StringBuilder("CARD NOT FOUND\n");
        assertEquals(expected.toString(),actual.addline(str).toString());
    }
}