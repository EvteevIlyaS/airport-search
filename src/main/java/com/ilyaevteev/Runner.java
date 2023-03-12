package com.ilyaevteev;

import com.ilyaevteev.exceptions.WrongColumnNumberException;
import com.ilyaevteev.handlings.AirportsDataManager;
import com.ilyaevteev.handlings.AirportsSearch;
import com.ilyaevteev.tools.CsvReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Runner {
    public static void runAirportSearchApp(String[] args) {
        int columnNumber = 0;

        try {
            // 1st case
            if (args.length == 0)
                throw new WrongColumnNumberException("Аргумент номера строки не задан");
            // 2nd case
            columnNumber = Integer.parseInt(args[0]);
            // 3rd case
            if (columnNumber < 1 | columnNumber > 14)
                throw new WrongColumnNumberException("Задан неверный номер столбца");
        } catch (NumberFormatException e) {
            System.out.println("Запустите программу с надлежащими параметрами (1-14) - " + e.getMessage());
            System.exit(0);
        }

        CsvReader csvReader = new CsvReader(columnNumber);
        TreeMap<String, ArrayList<Integer>> columnData = csvReader.getColumnData();

        Scanner scanner = new Scanner(System.in);
        int rowsAmount;
        long timeConsumed;
        List<Integer> resultIdx;
        for (; ; ) {
            System.out.println("Введите префикс строки для поиска:");
            String searchPrefix = scanner.nextLine();

            if (searchPrefix.equals("!quit")) System.exit(0);

            AirportsSearch airportsSearch = new AirportsSearch(columnData);

            airportsSearch.searchRows(searchPrefix);

            rowsAmount = airportsSearch.getRowsAmount();
            if (rowsAmount == 0) {
                System.out.println("В столбце " + columnNumber + " строки, начинающиеся с префикса \""
                        + searchPrefix + "\" не найдены, попробуйте снова");
                continue;
            }

            timeConsumed = airportsSearch.getTimeConsumed();
            resultIdx = airportsSearch.getIdxResultRows();

            AirportsDataManager airportsDataManager = new AirportsDataManager(columnNumber);
            airportsDataManager.getSortedResultRows(resultIdx).forEach(System.out::println);

            System.out.println("Количество найденных строк: " + rowsAmount +
                    ", затраченное время на поиск: " + timeConsumed + " мс");

        }
    }
}
