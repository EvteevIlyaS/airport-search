package com.ilyaevteev.tools;

import java.util.Arrays;
import java.util.Comparator;

public class PrefixMatch {
    private PrefixMatch() {
    }

    public static String[] prefixMatch(final String[] array, final String prefix) {
        final Comparator<String> PREFIX_COMPARATOR = (o1, o2) ->
                prefix.length() > o1.length() ? -1 :
                        o1.substring(0, prefix.length()).compareToIgnoreCase(o2);

        final int randomIndex = Arrays.binarySearch(array, prefix, PREFIX_COMPARATOR);

        if (randomIndex < 0) return new String[0];

        int rangeStarts = randomIndex, rangeEnds = randomIndex;

        while (rangeStarts > -1 && array[rangeStarts].toLowerCase().startsWith(prefix.toLowerCase()))
            rangeStarts--;

        while (rangeEnds < array.length && array[rangeEnds].toLowerCase().startsWith(prefix.toLowerCase()))
            rangeEnds++;

        return Arrays.copyOfRange(array, rangeStarts + 1, rangeEnds);
    }
}
