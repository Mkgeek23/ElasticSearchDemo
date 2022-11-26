package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Keyboard {

    private BufferedReader keyboard;

    public Keyboard() {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getDateFromKeyobard() throws IOException {
        System.out.print("> ");
        return keyboard.readLine();
    }

    public void close() {
        if (keyboard != null) {
            try {
                keyboard.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
