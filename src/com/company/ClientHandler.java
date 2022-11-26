package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintStream out;
    private ObjectOutputStream outSD;
    private BufferedReader in;
    private ClientData clientDataOnServer;
    private ServerData serverData;

    private ServerCommands serverCommands;

    private boolean shouldRun = true;

    public ClientHandler(Socket s, ServerData serverData) throws IOException {
        socket = s;
        out = new PrintStream(socket.getOutputStream());
        outSD = new ObjectOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.serverData = serverData;

        clientDataOnServer = new ClientData();

        serverCommands = new ServerCommands(this, serverData, clientDataOnServer);
    }

    public void run() {
        try {
            while (shouldRun) {
                String command = in.readLine();
                System.out.println(command);
                serverCommands.executeCommand(command);
            }
        } catch (SocketException e) {
            System.out.println("Client disconected.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        close();
    }

    public void sendTxtMessage(String message) {
        System.out.println(message);
        out.println(message);
    }

    public void sendSDMessage(SearchData message) {
        System.out.println("Sended message [SearchData]");
        try {
            outSD.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed sending message to client");
        }
    }


    public void close() {
        try {
            shouldRun = false;
            serverData.addChToRemove(this);
            in.close();
            out.close();
            outSD.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
