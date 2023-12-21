package org.dsen.latency;

import org.dsen.latency.util.TestPerformanceUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerHandler implements Runnable {

    private final Socket clientSocket;

    public ServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Read a message from the client
            in.readObject();
            TestPerformanceUtil.stop("request");
            // Send a response back to the client
            LocalDateTime responseTime = LocalDateTime.now();
            out.writeObject(responseTime);

            clientSocket.close();
            TestPerformanceUtil.start("response");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

