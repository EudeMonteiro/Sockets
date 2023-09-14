package sockets.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer {
    public static void main(String[] args) throws Exception {
        String clientSentence;
        String capitalizedSentence;

        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            boolean serverRunning = true; // Flag to control the server's state

            while (serverRunning) {
                try (Socket connectionSocket = welcomeSocket.accept();
                        BufferedReader inFromClient = new BufferedReader(
                                new InputStreamReader(connectionSocket.getInputStream()));
                        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream())) {

                    clientSentence = inFromClient.readLine();

                    if (clientSentence == null || clientSentence.equalsIgnoreCase("exit")) {
                        // If the client sends "exit" or the connection is closed, stop the server
                        serverRunning = false;
                    } else {
                        capitalizedSentence = clientSentence.toUpperCase() + '\n';
                        outToClient.writeBytes(capitalizedSentence);
                    }
                }
            }
        }
    }
}