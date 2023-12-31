package org.dsen.latency;

import org.dsen.latency.util.ProgressTracker;
import org.dsen.latency.util.TestPerformanceUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Client {

    public static void main(int invocations, ProgressTracker progressTracker) {
        String serverAddress = "localhost";
        int serverPort = 12345;
        for (int i = 0; i < invocations; i++) {
            try (
                    Socket socket = new Socket(serverAddress, serverPort);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                long startTime = System.currentTimeMillis();

                // Send a message to the server

                LocalDateTime requestTime = LocalDateTime.now();
                TestPerformanceUtil.start("request");
                out.writeObject(requestTime);

                // Receive the response from the server
                LocalDateTime responseTime = (LocalDateTime) in.readObject();
                TestPerformanceUtil.stop("response");
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                ZoneOffset zoneOffset = ZoneOffset.UTC;
                long response = responseTime.toEpochSecond(zoneOffset) - requestTime.toEpochSecond(zoneOffset);
/*
                System.out.println("Request: " + (totalTime - response) + " ms");
                System.out.println("Response: " + response + " ms");
                System.out.println("Total: " + totalTime + " ms");*/
                progressTracker.updateProgress();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

