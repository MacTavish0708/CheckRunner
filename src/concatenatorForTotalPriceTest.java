import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class concatenatorForTotalPriceTest {

    @Test
    void addline() {

        concatenatorForTotalPrice actual = new concatenatorForTotalPrice();
        double a=9.990075755531699E10;
        Integer b=  0;
        double c=9.9900749918057E9;

        StringBuilder expected = new StringBuilder(
                "TAXABLE TOTAL:             $99900757555,32\n" +
                "DISCOUNT:0%                          $0,00\n" +
                "STOCK DISCOUNT              $9990074991,81\n" +
                "TOTAL                      $89910682563,51\n" );
        assertEquals(expected.toString(),actual.addline(a,b,c).toString());
    }
}