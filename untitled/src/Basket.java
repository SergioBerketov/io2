import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Basket {

    private String[] products;
    private int[] prices;
    private int[] productCounter;
    private int[] allCategoriesCost;
    private int totalPrice;
    private static List<String> textData = new ArrayList<>();

    private File txtFile;

    public Basket () {

    }

    public Basket (String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.productCounter = new int[products.length];
        this.allCategoriesCost = new int[products.length];
    }

    public void addToCard (int productNum, int amount) {
        productCounter[productNum] += amount;
        int currentPrice = prices[productNum];
        int sum = amount * currentPrice;
        allCategoriesCost[productNum] += sum;
        totalPrice += sum;
    }

    public void saveTxt(File textFile) throws IOException {

        txtFile = new File(textFile.toURI());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {

            for (String i : products) {
                writer.write(i + " ");
            }
            writer.newLine();

            for (int i : prices) {
                writer.write(String.valueOf(i + " "));
            }
            writer.newLine();

            for (int i : productCounter) {
                writer.write(String.valueOf(i + " "));
            }
            writer.newLine();

            for (int i : allCategoriesCost) {
                writer.write(String.valueOf(i + " "));
            }
            writer.newLine();

            writer.write(String.valueOf(totalPrice));
            writer.flush();
        }
    }

    public static Basket loadFromTxtFile (File textFile) throws IOException {

        Basket basket = new Basket();

        try (BufferedReader reader = new BufferedReader( new FileReader(textFile))) {

            String prodStr = reader.readLine();
            String pricesStr = reader.readLine();
            String prodCounterStr = reader.readLine();
            String allCatCostStr = reader.readLine();
            String totalPriceStr = reader.readLine();

            basket.products = prodStr.split(" ");

            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer :: parseInt)
                    .mapToInt(Integer :: intValue)
                    .toArray();

            basket.productCounter = Arrays.stream(prodCounterStr.split( " "))
                    .map(Integer :: parseInt)
                    .mapToInt(Integer :: intValue)
                    .toArray();

            basket.allCategoriesCost = Arrays.stream(allCatCostStr.split(" "))
                    .map(Integer :: parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.totalPrice = Integer.parseInt(totalPriceStr);
        }
        return basket;
    }

    public void printCart () {

        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (productCounter[i] == 0) {
                continue;
            }else {
                System.out.println(products[i] + " " + productCounter[i] + " шт." +
                        prices[i] + " руб/шт., " + allCategoriesCost[i] + " руб. в сумме.");
            }
        }
        System.out.println("Общая сумма корзины: " + totalPrice);
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getProductCounter() {
        return productCounter;
    }

    public int[] getAllCategoriesCost() {
        return allCategoriesCost;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public File getTxtFile() {
        return txtFile;
    }

}
