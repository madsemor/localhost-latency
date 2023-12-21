package org.dsen.latency;

public class Main {
    public static void main(String[] args) {

        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        Client.main(null);
        server.stop();
        Client.main(null);
        System.out.println("Server stopped");


    }
}