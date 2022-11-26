package com.company;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker extends Thread {
    private boolean shouldRun = true;

    private ServerConnection serverConnection;

    private ClientCommands clientCommands;
    private ClientData clientData;

    private Keyboard keyboard;

    public ClientWorker(Socket socket) throws IOException {
        serverConnection = new ServerConnection(socket);
        clientData = new ClientData();

        keyboard = new Keyboard();

        clientCommands = new ClientCommands(this, clientData, keyboard, serverConnection);
    }

    public void start() {
        while (shouldRun) {
            try {
                String command = keyboard.getDateFromKeyobard();
                clientCommands.executeCommand(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    public void close() {
        shouldRun = false;
        try {
            if (keyboard != null) keyboard.close();
            if (serverConnection != null) serverConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
