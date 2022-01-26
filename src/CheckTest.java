import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

    @org.junit.jupiter.api.Test
    void printcheck() {

        String a ="50-6 1-2 2-25 3-4 4-50 5-6 6-7 7-8 8-50 9-1 10-999 11-999 12-4 13 32-6 33-7 34-8 0-9 card-999";
        String[] arg = a.split(" ");

        ArrayList<String> argS = new ArrayList<>();

        ArrayList<String> file = new ArrayList<>();

        //sorting files and data
        for (String s : arg) {

            if (s.contains("-")) argS.add(s);

            else file.add(s);

        }

        //choice between default and data from files
        if (file.isEmpty()) new DataClassDefaultOrFile().setDefault();

        else new DataClassDefaultOrFile().setFile(file);

        Check NewCheck = new Check(argS);

        //StringBuilder actual = NewCheck.printcheck();

        StringBuilder date= new StringBuilder(DateTimeFormatter.ofPattern("dd/MM/uuuu").format(LocalDate.now()));

        StringBuilder time= new StringBuilder(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()));

        StringBuilder expected = new StringBuilder(
                "               CASH RECEIPT\n" +
                "             Supermarket 5chka\n" +
                "Bedi 4 Minsk\n" +
                "Tel: 80291234567\n" +
                "Cashier №0001              Date:"+date+"\n"+
                "                           Time:"+time+"\n"+
                "------------------------------------------\n" +
                "QTY DESCRIPTION             PRICE    TOTAL\n" +
                "PRODUCT NOT FOUND\n" +
                " 2  Cucumbers             $103,40  $206,80\n" +
                " 25 Peppers               $129,60 $3240,00\n" +
                " 4  Cabbage                $33,14  $132,56\n" +
                " 50 Potatoes               $35,71 $1785,50\n" +
                " 6  Orange juice           $45,02  $270,12\n" +
                "    PROMOTION:-10%         $40,52  $243,11\n" +
                " 7  Oranges                $79,33  $555,31\n" +
                " 8  Bananas                $61,10  $488,80\n" +
                "    PROMOTION:-10%         $54,99  $439,92\n" +
                " 50 Pears                 $115,50 $5775,00\n" +
                "    PROMOTION:-10%        $103,95 $5197,50\n" +
                " 1  Kiwi                  $106,00  $106,00\n" +
                "999 Beef                $381,17 $380788,83\n" +
                "999 Chicken fillet      $286,38 $286093,62\n" +
                " 4  Pork                  $266,08 $1064,32\n" +
                "PRODUCT NOT FOUND\n" +
                "PRODUCT NOT FOUND\n" +
                "PRODUCT NOT FOUND\n" +
                " 9  Tomatoes               $92,14  $829,26\n" +
                "------------------------------------------\n" +
                "CARD NOT FOUND\n" +
                "TAXABLE TOTAL:                  $681336,12\n" +
                "DISCOUNT:0%                          $0,00\n" +
                "STOCK DISCOUNT                     $653,39\n" +
                "TOTAL                           $680682,73\n" );

        assertEquals(expected.toString(),NewCheck.printcheck().toString());
    }
}