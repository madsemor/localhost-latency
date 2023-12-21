package org.dsen.latency.util;

public class KeyData {

    private final String key;
    private long invocationCount = 0;
    private long totalTime = 0;
    private long startTime = 0;

    private boolean completed;
    private long minTime = Long.MAX_VALUE;
    private long maxTime = Long.MIN_VALUE;


    public KeyData(String key) {
        this.key = key;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        long stopTime = System.currentTimeMillis();
        long time = stopTime - startTime;
        totalTime = totalTime + time;
        invocationCount++;
        if (time < minTime) {
            minTime = time;
        }
        if (time > maxTime) {
            maxTime = time;
        }
        completed = true;
    }

    public String getKey() {
        return key;
    }

    public long getInvocationCount() {
        return invocationCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getMinTime() {
        return minTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    @Override
    public String toString() {
        return "KeyData{" +
                "key='" + key + '\'' +
                ", " + dynamicTab(key) + "invocationCount = " + invocationCount +
                ",\t totalTime=" + totalTime +
                ",\t minTime=" + minTime +
                ",\t maxTime=" + maxTime +
                ",\t avgTime=" + (invocationCount > 0 ? (totalTime / invocationCount) : 0) +
                '}';
    }

    private String dynamicTab(String key) {
        int count = key.length();
        StringBuilder spaces = new StringBuilder();
        for (int x = 0; x < 64 - count; x++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    public boolean isCompleted() {
        return completed;
    }
}
