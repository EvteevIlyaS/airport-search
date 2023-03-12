package com.ilyaevteev.handlings;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.ilyaevteev.tools.PrefixMatch.prefixMatch;

public class AirportsSearch {
    private final TreeMap<String, ArrayList<Integer>> columnData;

    private long timeConsumed;
    private int rowsAmount;

    private List<Integer> idxResultRows = new ArrayList<>();

    public AirportsSearch(TreeMap<String, ArrayList<Integer>> columnData) {
        this.columnData = columnData;
    }

    public void searchRows(String substring) {
        String[] fieldsData = columnData.keySet().toArray(String[]::new);

        long startTime = System.currentTimeMillis();

        // Бинарный поиск O(log n)
        String[] dataKeys = prefixMatch(fieldsData, substring);
        if (dataKeys.length == 0) return;
        setIdxResultRows(dataKeys);

        long endTime = System.currentTimeMillis();

        timeConsumed = endTime - startTime;
    }

    private void setIdxResultRows(String[] dataKeys) {
        for (String dataKey: dataKeys) idxResultRows.addAll(columnData.get(dataKey));
        rowsAmount = idxResultRows.size();
    }

    public long getTimeConsumed() {
        return timeConsumed;
    }

    public int getRowsAmount() {
        return rowsAmount;
    }

    public List<Integer> getIdxResultRows() {
        return idxResultRows;
    }
}


