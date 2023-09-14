package sockets.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            boolean serverRunning = true; // Flag to control the server's state

            while (serverRunning) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String sentence = new String(receivePacket.getData());
                InetAddress ipAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                serverSocket.send(sendPacket);

                // Check for an end condition (e.g., when a specific command is received)
                if (sentence.trim().equalsIgnoreCase("exit")) {
                    serverRunning = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
