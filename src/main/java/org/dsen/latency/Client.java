package org.dsen.latency;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (
                Socket socket = new Socket(serverAddress, serverPort);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            long startTime = System.currentTimeMillis();

            // Send a message to the server

            LocalDateTime requestTime = LocalDateTime.now();
            out.writeObject(requestTime);

            // Receive the response from the server
            LocalDateTime responseTime = (LocalDateTime) in.readObject();

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            ZoneOffset zoneOffset = ZoneOffset.UTC;
            long response = responseTime.toEpochSecond(zoneOffset) - requestTime.toEpochSecond(zoneOffset);

            System.out.println("Request: " + (totalTime- response) + " ms");
            System.out.println("Response: " + response + " ms");
            System.out.println("Total: " + totalTime + " ms");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

