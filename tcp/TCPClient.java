package sockets.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {
    private static final Logger logger = Logger.getLogger(TCPClient.class.getName());

    public static void main(String[] args) throws Exception {
        String sentence;
        String modifiedSentence;

        try (Socket clientSocket = new Socket()) {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Get the local IP address
            InetAddress localAddress = InetAddress.getLocalHost();
            String ipAddress = localAddress.getHostAddress();

            clientSocket.connect(new java.net.InetSocketAddress(ipAddress, 6789));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();

            // Use format specifier
            String logMessage = String.format("FROM SERVER: %s", modifiedSentence);
            logger.log(Level.INFO, logMessage);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }
}