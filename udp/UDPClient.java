package sockets.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {
    private static final Logger logger = Logger.getLogger(UDPClient.class.getName());

    public static void main(String[] args) throws Exception {
        try (BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
             DatagramSocket clientSocket = new DatagramSocket()) {

            InetAddress ipAddress = InetAddress.getByName("hostname");
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());

            // Use format specifier
            String logMessage = String.format("FROM SERVER: %s", modifiedSentence);
            logger.log(Level.INFO, logMessage);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }
}