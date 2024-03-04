package ru.bsd.json;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    protected static String[] products = {"Хлеб", "Молоко", "Колбаса"};
    protected static int[] prices = {30, 50, 100};
    protected static File saveFile = new File("basket.json");

    public static void main(String[] args) throws IOException {

        Basket basket = null;

        if (saveFile.exists()) {
            basket = Basket.loadFromJsonFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " руб/шт.");
        }

        while (true) {

            System.out.println("Выберите товар и количество, или введите end");
            String choice = scanner.nextLine().toLowerCase();
            String[] pieces = choice.split(" ");

            if ("end".equals(choice))
                break;

            int numberOfProduct = Integer.parseInt(pieces[0]) - 1;
            int amountOfProduct = Integer.parseInt(pieces[1]);
            basket.addToCard(numberOfProduct, amountOfProduct);
            ClientLog.log(numberOfProduct, amountOfProduct);
        }

        basket.printCart();
//        basket.saveTxt(new File(basketTxtFile.toURI()));
        basket.saveToJSON(new File(saveFile.toURI()));
        ClientLog.exportAsCSV(new File("csvLog.xml"));
    }
}