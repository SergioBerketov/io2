package ru.bsd.json;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ClientLog {

    public static Map<Integer, Integer> logger = new HashMap<>();

    //todo лучше работать со списком + много фишек. Смотри дз старое https://github.com/SergioBerketov/IOstreams-Working-With-Files-Serialization-1/blob/json/src/main/java/ru/netology/ClientLog.java

    public static void log(int productNum, int amount) {
        logger.put(productNum, amount);
    }

    public static void exportAsCSV(File txtFile) throws IOException {


        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {

            writer.writeAll(Collections.singleton(new String[]{"numberOfProduct, productCount"}));

            for (Map.Entry<Integer, Integer> entry : logger.entrySet()) {

                StringJoiner contactString = new StringJoiner(",")
                        .add(entry.getKey().toString())
                        .add(entry.getValue().toString());

                String[] contactArrayForCsv = contactString.toString().split(",");
                writer.writeNext(contactArrayForCsv);

            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
