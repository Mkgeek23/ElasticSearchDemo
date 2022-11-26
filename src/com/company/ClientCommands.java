package com.company;

import java.io.IOException;
import java.util.ArrayList;

public class ClientCommands implements Commands {

    LoginDataValidator ldv = new LoginDataValidator();

    ClientWorker clientWorker;
    ClientData clientData;

    Keyboard keyboard;

    ServerConnection serverConnection;

    public ClientCommands(ClientWorker clientWorker, ClientData clientData, Keyboard keyboard, ServerConnection serverConnection) {
        this.clientWorker = clientWorker;
        this.clientData = clientData;
        this.keyboard = keyboard;
        this.serverConnection = serverConnection;
    }

    @Override
    public void executeCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("/exit")) clientWorker.close();
        else if (command.equals("/help")) helpCommand();
        else if (!clientData.getLogged()) {
            if (command.equals("/remind")) remindCommand();
            else if (command.equals("/register")) registerCommand();
            else if (command.equals("/login")) loginCommand();
            else {
                System.err.println("Invalid command!");
                helpCommand();
            }
        } else if (clientData.getLogged()) {
            if (command.equals("/search")) searchCommand();
            else if (command.equals("/logout")) logoutCommand();
            else {
                System.err.println("Invalid command!");
                helpCommand();
            }
        }
    }

    private void helpCommand() {
        System.out.println("Available commands:");
        if (!clientData.getLogged())
            System.out.println("'/login' - zostaniesz poproszony o podanie loginu i hasla w celu zalogowania.\n" +
                    "'/register' - zostaniesz poproszony o podanie loginu i hasla w celu rejestracji.\n" +
                    "'/remind' - zostaniesz poproszony o podanie loginu w celu przypomnienia hasla.\n" +
                    "'/exit' - Kończy działanie programu programu.");
        else
            System.out.println("'/search' - zostaniesz poproszony o wpisanie frazy, którą chcesz wyszukać w zbiorze plików na serwerze.\n" +
                    "'/logout' - zostaniesz wylogowany.\n" +
                    "'/exit' - Kończy działanie programu programu.");
    }

    private void registerCommand() {
        boolean loginCorrect = false, passwordCorrect = false;
        String login = "", password = "", password2;
        try {
            while (!loginCorrect) {
                System.out.println("Enter login (from 3 to 12 characters; available letters and digits)>");
                login = keyboard.getDateFromKeyobard();
                loginCorrect = ldv.loginValidate(login);
            }
            while (!passwordCorrect) {
                System.out.println("Enter password  (from 4 to 16 characters)>");
                password = keyboard.getDateFromKeyobard();
                passwordCorrect = ldv.passwordValidate(password);
                if (!passwordCorrect) continue;
                System.out.println("Enter password again>");
                password2 = keyboard.getDateFromKeyobard();
                passwordCorrect = password.equals(password2);
                if (!passwordCorrect) System.out.println("Passwords are different");
            }
            serverConnection.sendMessage("/register " + login + " " + password);
            System.out.println(serverConnection.getTxtMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginCommand() {
        String login, password;
        try {
            System.out.println("Enter login:");
            login = keyboard.getDateFromKeyobard();
            System.out.println("Enter password:");
            password = keyboard.getDateFromKeyobard();
            if (ldv.validateLoginAndPassword(login, password)) {
                serverConnection.sendMessage("/login " + login + " " + password);
                String mess = serverConnection.getTxtMessage();
                System.out.println(mess);
                if (mess.toLowerCase().contains("logged")) clientData.setLogged(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchCommand() {
        String phrase;
        try {
            System.out.println("Enter phrase:");
            phrase = keyboard.getDateFromKeyobard();
            if (phrase.length() > 0)
                serverConnection.sendMessage("/search " + phrase);
            SearchData mess = serverConnection.getSDMessage();
            if (mess != null) printResults(mess);
            else System.out.println("An error occured while searching");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logoutCommand() {
        serverConnection.sendMessage("/logout ");
        clientData.setLogged(false);
        System.out.println("Logout correctly!");
    }

    private void remindCommand() {
        String login;
        try {
            System.out.println("Enter login:");
            login = keyboard.getDateFromKeyobard();
            if (ldv.loginValidate(login)) {
                serverConnection.sendMessage("/remind " + login + " ");
                System.out.println(serverConnection.getTxtMessage());
            } else System.out.println("Incorrect login or password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printResults(SearchData sd) {
        ArrayList<String> authors = sd.getAuthors();
        ArrayList<String> titles = sd.getTitles();

        System.out.println("Results: ");

        for (int i = 0; i < authors.size(); i++) {
            System.out.println(i + 1 + ".");
            System.out.println("Title: " + titles.get(i));
            System.out.println("Author: " + authors.get(i));
        }
    }

}
