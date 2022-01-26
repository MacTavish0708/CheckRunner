import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

//  "C:\Program Files\Java\jdk-9.0.4\bin\javac.exe" -d bin src/CheckRunner.java
//  "C:\Program Files\Java\jdk-9.0.4\bin\java.exe" -cp ./bin CheckRunner
//  "C:\Program Files\Java\jdk-9.0.4\bin\java.exe" -cp ./bin CheckRunner 3-1 2-5 5-1 card-1234

public class CheckRunner {

    public static void main(String[] args) {

        //for testing(arg instead of args)
        //String a = new String("50-6 1-2 2-25 3-4 4-50 5-6 6-7 7-8 8-50 9-1 10-999 11-999 12-4 13-55 14-999 15-7 16-8 17-9 18-25 19-2 20-50 21-4 22-5 23-6 24-7 25-8 26-999 27-1 28-2 29-3 30-4 31-5 32-6 33-7 34-8 0-9 card-999 productsFile.txt auctionFile.txt cardsFile.txt");
        //String a = new String("50-6 1-2 2-25 3-4 4-50 5-6 6-7 7-8 8-50 9-1 10-999 11-999 12-4 13 32-6 33-7 34-8 0-9 card-999");
        //String[] arg = a.split(" ");

        ArrayList<String> argS = new ArrayList<>();

        ArrayList<String> file = new ArrayList<>();

        //sorting files and data
        for (String s : args) {

            if (s.contains("-")) argS.add(s);

            else file.add(s);

        }

        //choice between default and data from files
        if (file.isEmpty()) new DataClassDefaultOrFile().setDefault();

        else new DataClassDefaultOrFile().setFile(file);

        Check NewCheck = new Check(argS);

        StringBuilder print = new StringBuilder(NewCheck.printcheck());

        //console output
        System.out.print(print);

        //output to file
        try (FileOutputStream fileOut = new FileOutputStream("./src/fileOut.txt")) {

            fileOut.write(print.toString().getBytes(StandardCharsets.UTF_8), 0, print.toString().length() + 1);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}

class Check {

    private final ArrayList<String> IdQtyAndCard;

    public Check(ArrayList<String> IdQtyAndCard){
        this.IdQtyAndCard=IdQtyAndCard;
    }

    //top of check
    private final StringBuilder NameStore = new StringBuilder("Supermarket 5chka");

    private final StringBuilder StoreAdress = new StringBuilder("Bedi 4 Minsk");

    private final StringBuilder Tel = new StringBuilder("80291234567");

    private final StringBuilder CashierNumber= new StringBuilder("0001");

    private final StringBuilder date= new StringBuilder(DateTimeFormatter.ofPattern("dd/MM/uuuu").format(LocalDate.now()));

    private final StringBuilder time= new StringBuilder(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()));

    private final StringBuilder line = new StringBuilder("------------------------------------------\n");

    private final StringBuilder receipt= new StringBuilder(
            "               CASH RECEIPT\n             "
            +NameStore
            +"\n"+ StoreAdress
            +"\nTel: "+Tel
            +"\nCashier \u2116"+CashierNumber+"              Date:"+date
            +"\n                           Time:"+time
            +"\n"+line
            +"QTY DESCRIPTION             PRICE    TOTAL\n");

    private boolean DiscountCardPresented;

    concatenatorForProduct cfp = new concatenatorForProduct();

    concatenatorForDiscountCard cfdc = new concatenatorForDiscountCard();

    concatenatorForTotalPrice cftp = new concatenatorForTotalPrice();

    public StringBuilder printcheck(){

        StringBuilder field = new StringBuilder();

        for (String iqac: IdQtyAndCard) {

            if (iqac.contains("card") & !DiscountCardPresented) {

                DiscountCardPresented = true;

                field = cfdc.addline(iqac);

            }

            else receipt.append(cfp.addline(iqac));

        }

        return receipt.append(line.append(field.append(cftp.addline(cfp.getTotalPrice(), cfdc.getDiscount(), cfp.getTotalPriceAuction()))));

    }

}

interface Concatenator {

    StringBuilder addline(String iqac);

    StringBuilder addline(double totalprice, Integer discount, double auctionDiscount);

}

class concatenatorForProduct implements Concatenator {

    private Double totalPrice= (double) 0;

    private double totalPriceAuction= 0;

        @Override

    public StringBuilder addline(String  iqac) {

        int id = Integer.parseInt(iqac.substring(0, iqac.indexOf("-")));

        int qty = Integer.parseInt(iqac.substring(iqac.indexOf("-") + 1));

        DataClassDefaultOrFile.Products bufferProduct = new DataClassDefaultOrFile.Products(id);

        StringBuilder bufferReceipt=new StringBuilder();

        try{
            String bufferNameProduct = bufferProduct.getNameProduct();

            StringBuilder bufferAuctionProduct = new StringBuilder();

            StringBuilder bufferPriceAndTotal = new StringBuilder();

            totalPrice += bufferProduct.getPriceProduct()*qty;

            if(qty>5 & bufferProduct.getBoolAuctionProduct()) {

                totalPriceAuction+=bufferProduct.getPriceProduct()*qty*0.1;

                String bufferTotalAuctionPrice = new forNumberClass().dfFormatNumber(qty * bufferProduct.getPriceProduct() * 0.9);
                String nSpaceForBufferAuctionProduct2 = new forNumberClass().nSpace(8, bufferTotalAuctionPrice);
                nSpaceForBufferAuctionProduct2 = nSpaceForBufferAuctionProduct2.length()==0? nSpaceForBufferAuctionProduct2.concat(" "):nSpaceForBufferAuctionProduct2;

                String bufferAuctionPrice = new forNumberClass().dfFormatNumber(bufferProduct.getPriceProduct() * 0.9);
                String nSpaceForBufferAuctionProduct1 = new forNumberClass().nSpace(22,  bufferAuctionPrice, nSpaceForBufferAuctionProduct2, bufferTotalAuctionPrice);
                nSpaceForBufferAuctionProduct1 = nSpaceForBufferAuctionProduct1.length()==0? nSpaceForBufferAuctionProduct1.concat("\n").concat(new forNumberClass().nSpace(40, bufferAuctionPrice, nSpaceForBufferAuctionProduct2, bufferTotalAuctionPrice)):nSpaceForBufferAuctionProduct1;

                bufferAuctionProduct.append("    PROMOTION:-10%").append(nSpaceForBufferAuctionProduct1).append("$").append(bufferAuctionPrice).append(nSpaceForBufferAuctionProduct2).append("$").append(bufferTotalAuctionPrice).append("\n");
            }

            String bufferTotalPrice = new forNumberClass().dfFormatNumber(qty*bufferProduct.getPriceProduct());
            String nSpaceForBufferPriceAndTotal = new forNumberClass().nSpace(8, bufferTotalPrice);
            nSpaceForBufferPriceAndTotal = nSpaceForBufferPriceAndTotal.length()==0? nSpaceForBufferPriceAndTotal.concat(" "):nSpaceForBufferPriceAndTotal;

            String bufferPrice = new forNumberClass().dfFormatNumber(bufferProduct.getPriceProduct());
            String nSpaceForBufferNameProduct = new forNumberClass().nSpace(36, bufferProduct.getNameProduct(), bufferPrice, nSpaceForBufferPriceAndTotal, bufferTotalPrice);
            nSpaceForBufferNameProduct = nSpaceForBufferNameProduct.length()==0? nSpaceForBufferNameProduct.concat("\n").concat(new forNumberClass().nSpace(40, bufferPrice, nSpaceForBufferPriceAndTotal, bufferTotalPrice)):nSpaceForBufferNameProduct;

            bufferPriceAndTotal.append(nSpaceForBufferNameProduct).append("$").append(bufferPrice).append(nSpaceForBufferPriceAndTotal).append("$").append(bufferTotalPrice).append("\n");

            bufferReceipt.append(qty/10==0?(" "+qty+"  "):(qty/100==0?(" "+qty+" "):(qty+" "))).append(bufferNameProduct).append(bufferPriceAndTotal).append(bufferAuctionProduct);

        }
        catch (IndexOutOfBoundsException e){
            bufferReceipt.append("PRODUCT NOT FOUND\n");
        }

            return bufferReceipt;
    }

    @Override
    public StringBuilder addline(double totalprice, Integer discount, double auctionDiscount) {
        return null;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public double getTotalPriceAuction(){
        return totalPriceAuction;
    }

}

class concatenatorForDiscountCard implements Concatenator {

    private int discount=0;

    @Override

    public StringBuilder addline(String CardNumber) {

        String cardCheck =CardNumber.substring(CardNumber.indexOf("-") + 1);

        DataClassDefaultOrFile.Cards bufferCard = new DataClassDefaultOrFile.Cards(cardCheck);

        StringBuilder numberCardsPresent = new StringBuilder();

        try{
            discount=bufferCard.genDiscountForCard();

            numberCardsPresent.append("Discount card number:").append(cardCheck).append(", discount:").append(discount).append("%\n");
        }
        catch (NullPointerException ex){
            numberCardsPresent.append("CARD NOT FOUND\n");
        }

        return numberCardsPresent;

    }

    @Override
    public StringBuilder addline(double totalprice, Integer discount, double auctionDiscount) {
        return null;
    }

    public Integer getDiscount(){
        return discount;
    }
}

class concatenatorForTotalPrice implements Concatenator{

    @Override
    public StringBuilder addline(double totalprice,Integer discount, double auctionDiscount) {

        String dftotalprice = new forNumberClass().dfFormatNumber(totalprice);
        String dfAuctionDiscount = new forNumberClass().dfFormatNumber(auctionDiscount);
        String dfTaxableTotalAndDescription = new forNumberClass().dfFormatNumber((totalprice-auctionDiscount)*(double)discount/100);
        String dfTotalPrice = new forNumberClass().dfFormatNumber((totalprice-auctionDiscount)*(1-(double)discount/100));


        String nSpaceForTaxableTotal = new forNumberClass().nSpace(27, dftotalprice);

        String nSpaceForTaxableTotalAndDescription = new forNumberClass().nSpace(31, String.valueOf(discount), dfTaxableTotalAndDescription);

        String nSpaceForAucttionDiscount = new forNumberClass().nSpace(27, dfAuctionDiscount);

        String nSpaceForTotal = new forNumberClass().nSpace(36, dfTotalPrice);

        StringBuilder bufferTotalPriceAndDiscount = new StringBuilder();

        return bufferTotalPriceAndDiscount
                .append("TAXABLE TOTAL:").append(nSpaceForTaxableTotal).append("$").append(dftotalprice)
                .append("\nDISCOUNT:").append(discount).append("%").append(nSpaceForTaxableTotalAndDescription).append("$").append(dfTaxableTotalAndDescription)
                .append("\nSTOCK DISCOUNT").append(nSpaceForAucttionDiscount).append("$").append(dfAuctionDiscount)
                .append("\nTOTAL").append(nSpaceForTotal).append("$").append(dfTotalPrice).append("\n");
    }

    @Override
    public StringBuilder addline(String iqac) {
        return null;
    }

}

class DataClassDefaultOrFile{

    static ArrayList<productsClass> productsClassArrayList=new ArrayList<>();

    static ArrayList<Integer> numberAuctionProduct = new ArrayList<>();

    static HashMap<String,Integer> cardAndItsDiscount = new HashMap<>();

    private final String[] nameProduct = new String[]{
            "Tomatoes", "Cucumbers", "Peppers", "Cabbage", "Potatoes",
            "Orange juice", "Oranges", "Bananas", "Pears", "Kiwi",
            "Beef","Chicken fillet","Pork","Boiled sausage","Semi-smoked sausage"
    };

    private final double[] priceProduct= new double[]{
            92.14,103.40,129.60,33.14,35.71,
            45.02,79.33,61.10,115.50,106.00,
            381.17,286.38,266.08,367.43,475.04
    };

    private final Integer[] auctionProduct= new Integer[]{5,9,8,7};

    private final String[] codeCard = new String[]{"1234","0000","1111","9999"};

    private final Integer[] discountCard = new Integer[]{5,9,8,7};

    public void setProductsClassArrayList(){
        for (int i = 0; i < nameProduct.length; i++) {
            productsClassArrayList.add(new productsClass(nameProduct[i],priceProduct[i]));
        }

    }

    public void setNumberAuctionProduct(){
        numberAuctionProduct.addAll(Arrays.asList(auctionProduct));
    }

    public void setCardAndItsDiscount(){
        for (int i = 0; i < codeCard.length; i++) {
            cardAndItsDiscount.put(codeCard[i],discountCard[i]);
        }

    }

    public void setDefault(){

        this.setProductsClassArrayList();

        this.setNumberAuctionProduct();

        this.setCardAndItsDiscount();

    }

    boolean pF=false;

    boolean cF=false;

    boolean aF=false;

    boolean pF1=false;

    boolean cF1=false;

    boolean aF1=false;

    public void setFile(ArrayList<String> file){

        for (String arr: file) {

            String[] bufferArr;

            if (arr.contains("productsFile")){

                pF=true;

                if (pF1) System.out.println("Data from "+arr+" file already loaded");

                else {

                    try (FileInputStream fileIn = new FileInputStream("./src/"+arr);) {

                    bufferArr =  new String(fileIn.readAllBytes()).split("[\\r\\n]+");

                    for (String s : bufferArr) {

                        String bufferNaneProduct = s.substring(0, s.indexOf(":"));

                        double bufferPriceProduct = Double.parseDouble(s.substring(s.indexOf(":") + 1));

                        productsClassArrayList.add(new productsClass(bufferNaneProduct, bufferPriceProduct));

                        pF1=true;

                    }

                    } catch (FileNotFoundException e){
                        System.out.println("FILE NOT FOUND\nThe file "+arr+ " must be in './src/"+arr+"'\nWill use default product data");
                        pF=false;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            else if (arr.contains("cardsFile")){

                cF=true;

                if (cF1) System.out.println("Data from "+arr+" file already loaded");

                else {

                    try (FileInputStream fileIn = new FileInputStream("./src/" + arr);) {


                        bufferArr = new String(fileIn.readAllBytes()).split("[\\r\\n]+");

                        for (String s : bufferArr) {

                            String bufferCodeCard = s.substring(0, s.indexOf("-"));

                            Integer bufferDiscountCard = Integer.parseInt(s.substring(s.indexOf("-") + 1));

                            cardAndItsDiscount.put(bufferCodeCard, bufferDiscountCard);

                            cF1=true;

                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("FILE NOT FOUND\nThe file " + arr + " must be in './src/" + arr + "'\nWill use default data of discount cards");
                        cF = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            else if(arr.contains("auctionFile")){

                aF=true;

                if (aF1) System.out.println("Data from "+arr+" file already loaded");

                else {

                    try (FileInputStream fileIn = new FileInputStream("./src/" + arr);) {

                        bufferArr = new String(fileIn.readAllBytes()).split("[\\r\\n]+");

                        for (String s : bufferArr) {
                            numberAuctionProduct.add(Integer.valueOf(s));
                        }

                        aF=true;

                    } catch (FileNotFoundException e) {
                        System.out.println("FILE NOT FOUND\nThe file " + arr + " must be in './src/" + arr + "'\nWill use default data of promotional products");
                        aF = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            else System.out.println("The "+arr+" file must have the same name: auctionFile.txt, cardsFile.txt, productsFile.txt\nDepending on what he keeps in himself");

        }

        if (!pF) this.setProductsClassArrayList();
        if (!cF) this.setCardAndItsDiscount();
        if (!aF) this.setNumberAuctionProduct();

    }

    static class Products{

        private final int Id;

        public Products(int Id){
            this.Id=Id;
        }

        public String getNameProduct(){
            return productsClassArrayList.get(Id).getNameProduct();
        }

        public double getPriceProduct(){
            return productsClassArrayList.get(Id).getPriceProduct();
        }

        public boolean getBoolAuctionProduct(){
            return numberAuctionProduct.contains(Id);
        }

    }

    static class Cards{

        private final String card;

        public Cards(String card){
            this.card=card;
        }

        public Integer genDiscountForCard(){
            return cardAndItsDiscount.get(card);
        }

    }

}

class productsClass {

    private final String nameProduct;

    private final double priceProduct;

    public productsClass(String nameProduct, double priceProduct){
        this.nameProduct=nameProduct;
        this.priceProduct=priceProduct;
    }

    public String getNameProduct(){
        return nameProduct;
    }

    public double getPriceProduct(){
        return priceProduct;
    }

}

class forNumberClass {

    public String nSpace(int a, String b, String c, String d, String e){
        return String.join("", Collections.nCopies(Math.max(a - b.length()-c.length()-d.length()-e.length(), 0)," "));
    }

    public String nSpace(int a, String b, String c, String d){
        return nSpace(a, b, c, d, "");
    }

    public String nSpace(int a, String b, String c){
        return nSpace(a, b, c, "", "");
    }

    public String nSpace(int a, String b){
        return nSpace(a, b, "", "", "");
    }

    public String dfFormatNumber(double number){
        return String.format("%.2f",number);
    }

}