package com.company;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Server {

    private static final int PORT = 9090; //Serwer port
    private static final String USERDATABASEFILENAME = "users.xml";


    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "paiproject.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        try {
            ServerWorker sw = new ServerWorker(USERDATABASEFILENAME);

            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket ss = ssf.createServerSocket(PORT);
            sw.start();
            System.out.println("[Server] Server up & ready to connections...");

            while (true) {
                System.out.println("[Server] Waiting for client connection...");
                Socket s = ss.accept();
                sw.addSocket(s);

                System.out.println("[Server] Connected to client!");

            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
