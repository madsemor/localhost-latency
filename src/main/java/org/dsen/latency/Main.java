package org.dsen.latency;

import org.dsen.latency.util.TestPerformanceUtil;

public class Main {

    private static final int invocations = 10;
    public static void main(String[] args) {

        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        Client.main(invocations);
        server.stop();
        Client.main(1);
        System.out.println("Server stopped");
        TestPerformanceUtil.statistic();

    }
}