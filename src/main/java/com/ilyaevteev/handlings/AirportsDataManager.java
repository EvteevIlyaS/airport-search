package com.ilyaevteev.handlings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class AirportsDataManager {
    private final int columnNumber;

    public AirportsDataManager(int columnNumber) {
        this.columnNumber = columnNumber - 1;
    }

    public List<String> getSortedResultRows(List<Integer> idxResultRows) {
        List<String> resultRows = new ArrayList<>();

        // Считываем не весь файл, а построчно, до того момента, пока не будут получены все строки из списка индексов
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
                .getResourceAsStream("/airports.csv")))) {
            fillLinesByIndex(br, idxResultRows, resultRows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sortResultRows(resultRows);

        return resultRows.stream().map(row -> getColumnValue(row) + "[" + row + "]").collect(Collectors.toList());
    }

    private void fillLinesByIndex(BufferedReader br, List<Integer> idxResultRows, List<String> resultRows)
            throws IOException {
        idxResultRows.sort(Integer::compare);
        Collections.reverse(idxResultRows);

        Stack<Integer> idxStack = new Stack<>();
        idxStack.addAll(idxResultRows);

        String nextLine;

        int currentIdx = 0; int stIdx = idxStack.pop();
        do {
            nextLine = br.readLine();
            if (currentIdx == stIdx) {
                resultRows.add(nextLine);
                stIdx = idxStack.pop();
            }
            currentIdx++;
        }
        while (!idxStack.empty());
    }


    private void sortResultRows(List<String> resultRows) {
        final Comparator<String> ROWS_COMPARATOR = (o1, o2) -> {
            o1 = getColumnValue(o1); o2 = getColumnValue(o2);
            try {
                Double num1 = Double.parseDouble(o1); Double num2 = Double.parseDouble(o2);
                return num1.compareTo(num2);
            } catch (NumberFormatException e) {
                return o1.compareToIgnoreCase(o2);
            }
        };

        resultRows.sort(ROWS_COMPARATOR);
    }

    private String getColumnValue(String line) {
        // Разделить по запятой, только если после нее нет пробела
        return line.split(",(?!\\s)")[columnNumber];
    }

}
