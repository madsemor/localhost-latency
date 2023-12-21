package org.dsen.latency.util;


import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ProgressTracker {

    private int progress = 0;
    private int max = 0;
    private long startTime;
    private String task;

    public ProgressTracker(int max, String task) {
        this.max = max;
        progress = 0;
        startTime = System.currentTimeMillis();
        this.task = task;
        System.out.print("\n");
    }

    public void updateProgress() {
        progress++;
        updateProgress(startTime, max, progress, task);
    }

    static void updateProgress(long startTime, int total, int current, String task) {
        long eta = current == 0 ? 0 :
                (total - current) * (System.currentTimeMillis() - startTime) / current;

        String etaHms = current == 0 ? "N/A" :
                String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                        TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "#")))
                .append('#')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies(current == 0 ? (int) (Math.log10(total)) : (int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d " + task + ", ETA: %s", current, total, etaHms));

        System.out.print(string);
    }
}