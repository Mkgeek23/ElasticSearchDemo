package com.company;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;


public class Client {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9090;

    private static ClientWorker clientWorker;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "paiproject.jks");

        Socket s = null;

        try {
            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            s = ssf.createSocket(SERVER_IP, SERVER_PORT);

            clientWorker = new ClientWorker(s);
            clientWorker.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Connection to the server failed");
        } finally {
            if (clientWorker != null) clientWorker.close();
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}