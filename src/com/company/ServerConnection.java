package com.company;

import java.io.*;
import java.net.Socket;

public class ServerConnection {
    private final PrintWriter out;
    private final BufferedReader inTXT;
    private final ObjectInputStream inSD;

    public ServerConnection(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.inTXT = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.inSD = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getTxtMessage() {
        try {
            return inTXT.readLine();
        } catch (IOException e) {
            System.err.println("Failed to receive message | Connection was broken");
            try {
                close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    public SearchData getSDMessage() {
        try {
            return (SearchData) inSD.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws IOException {
        if (inSD != null) inSD.close();
        if (inTXT != null) inTXT.close();
        if (out != null) out.close();
    }


}
