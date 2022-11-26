package com.company;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread {

    private final ArrayList<ClientHandler> chList = new ArrayList<>();

    ServerData sd;
    JavaXmlDomRW xml;

    public ServerWorker(String userDatabase) throws IOException, ParserConfigurationException, SAXException {
        sd = new ServerData();
        xml = new JavaXmlDomRW(userDatabase);
        sd.setUsers(xml.getUsersDatabase());
    }

    public void run() {
        while (sd.isShouldRun()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("InterruptedException in Serwer Worker");
            }
            registerNewUser();
            removeClient();
        }
        close();
    }

    private void removeClient() {
        for (ClientHandler ch : sd.getChToRemove()) {
            sd.removeChToRemove(ch);
            chList.remove(ch);
        }
    }

    public void addSocket(Socket socket) {
        try {
            ClientHandler ch = new ClientHandler(socket, sd);
            ch.start();
            chList.add(ch);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to create a thread for connecting to the server");
        }
    }

    private void registerNewUser() {
        if (sd.isNewUser()) {
            try {
                xml.saveToDatabase(sd.getNewUsers());
            } catch (TransformerException | ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
                System.err.println("New user registration problem");
            }
        }
    }

    private void close() {
        for (ClientHandler clientHandler : chList) {
            if (clientHandler != null) clientHandler.close();
        }
    }
}
