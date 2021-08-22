package server;

import persistance.Database;

import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationServer {

    public static void main(String[] args) {
        final int port = 2000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Waiting for Clients...");
            while (true) {
                Socket sock = server.accept();
                new ServerThread(sock).start();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
