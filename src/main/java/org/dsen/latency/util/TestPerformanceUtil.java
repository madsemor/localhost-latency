package org.dsen.latency.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class TestPerformanceUtil {


    private static final Map<String, KeyData> data = new HashMap<>();

    private TestPerformanceUtil() {
    }

    public static void start(String key) {
        data.computeIfAbsent(key, k -> data.put(key, new KeyData(key)));
        KeyData keyData = data.get(key);
        keyData.start();

    }

    public static void stop(String key) {
        if (data.containsKey(key)) {
            KeyData keyData = data.get(key);
            keyData.stop();
        }
    }

    public static void statistic() {
        data.values().stream()
                .filter(KeyData::isCompleted)
                .sorted(Comparator.comparingLong(KeyData::getTotalTime))
                .forEach(System.out::println);
    }
}
