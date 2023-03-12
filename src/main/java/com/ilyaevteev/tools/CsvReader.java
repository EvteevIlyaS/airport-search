package com.ilyaevteev.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class CsvReader {
    private final int columnIndexToExtract;
    // Храним данные только из одной колонки, а не все данные из файла
    private final TreeMap<String, ArrayList<Integer>> columnData = new TreeMap<>();

    public CsvReader(int columnIndexToExtract) {
        // Нумерация начинается с 1, а не с 0
        this.columnIndexToExtract = columnIndexToExtract - 1;
        fillColumnData();
    }

    private void fillColumnData() {
        InputStreamReader streamReader = new InputStreamReader(getClass().getResourceAsStream("/airports.csv"));
//        String filePath = "src/main/resources/airports.csv";
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        try (BufferedReader reader = new BufferedReader(streamReader)) {
            String[] values; String line; int idxLine = 0;

            while ((line = reader.readLine()) != null) {
                // Разделить по запятой, только если после нее нет пробела
                values = line.split(",(?!\\s)");
                if (values.length > columnIndexToExtract) {
                    String data = values[columnIndexToExtract].replace("\"", "");

                    if(!columnData.containsKey(data)) columnData.put(data, new ArrayList<>());
                    columnData.get(data).add(idxLine);

                    idxLine++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TreeMap<String, ArrayList<Integer>> getColumnData() {
        return columnData;
    }

}


