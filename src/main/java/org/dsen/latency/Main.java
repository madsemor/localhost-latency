package org.dsen.latency;

import org.dsen.latency.util.ProgressTracker;
import org.dsen.latency.util.TestPerformanceUtil;

public class Main {

    private static final int invocations = 15000;
    public static void main(String[] args) {
        ProgressTracker progressTracker = new ProgressTracker(invocations, "localhost to localhost test");
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        Client.main(invocations, progressTracker);
        server.stop();
        Client.main(1, progressTracker);
        System.out.println("Server stopped");
        TestPerformanceUtil.statistic();

    }
}