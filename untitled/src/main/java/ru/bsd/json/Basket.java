package ru.bsd.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public Basket() {

    }

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.productCounter = new int[products.length];
        this.allCategoriesCost = new int[products.length];
    }

    public void addToCard(int productNum, int amount) {
        productCounter[productNum] += amount;
        int currentPrice = prices[productNum];
        int sum = amount * currentPrice;
        allCategoriesCost[productNum] += sum;
        totalPrice += sum;
    }

    public void saveTxt(File textFile) throws IOException {

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

    public static Basket loadFromTxtFile(File textFile) throws IOException {

        Basket basket = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {

            String prodStr = reader.readLine();
            String pricesStr = reader.readLine();
            String prodCounterStr = reader.readLine();
            String allCatCostStr = reader.readLine();
            String totalPriceStr = reader.readLine();

            basket.products = prodStr.split(" ");

            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.productCounter = Arrays.stream(prodCounterStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.allCategoriesCost = Arrays.stream(allCatCostStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.totalPrice = Integer.parseInt(totalPriceStr);
        }
        return basket;
    }

    public void saveToJSON(File jsonFile) throws IOException {

        //todo работает отлично, но в одну строчку пишет пока в коммент

//        try (FileWriter writer = new FileWriter(new File(jsonFile.toURI()))) {
//            Gson gson = new Gson();
//            String json = gson.toJson(this); // обратились к полям
//            writer.write(json);
//        }
        //todo так в файл пишет в одну строчку, так что ищем решение (используем джейсон билдер):

        try (FileWriter writer = new FileWriter(new File(jsonFile.toURI()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //todo эта настройка позволяет писать красиво больше ничего не меняли
            String json = gson.toJson(this); // обратились к полям
            writer.write(json);
        }

    }


    public static Basket loadFromJsonFile(File jsonFile) throws IOException {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) { //важная конструкция
                stringBuilder.append(line);
            }
            Gson gson = new Gson();
            //теперь тут реализуем объект (обратный метод ИЗ джейсона)
            basket = gson.fromJson(stringBuilder.toString(), Basket.class);
        }
        return basket;
    }


    public void printCart() {

        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (productCounter[i] == 0) {
                continue;
            } else {
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


}
