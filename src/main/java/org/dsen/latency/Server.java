package org.dsen.latency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server  implements Runnable {

    private boolean run = true;

    @Override
    public void run() {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (run) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client request in a separate thread
                Thread thread = new Thread(new ServerHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        this.run = false;
    }
}

