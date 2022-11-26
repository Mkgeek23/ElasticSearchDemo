package com.company;

import java.util.ArrayList;

public class ServerCommands implements Commands {


    private final LoginDataValidator ldv = new LoginDataValidator();

    private final MessageFormator messageFormator;
    private final ClientHandler clientHandler;
    private final ServerData serverData;

    private final ClientData clientDataOnServer;

    public ServerCommands(ClientHandler clientHandler, ServerData serverData, ClientData clientDataOnServer) {
        messageFormator = new MessageFormator();
        this.clientHandler = clientHandler;
        this.serverData = serverData;
        this.clientDataOnServer = clientDataOnServer;
    }

    @Override
    public void executeCommand(String command) {
        if (!clientDataOnServer.getLogged())
            switch (messageFormator.getCommandFromString(command)) {
                case "/login":
                    loginCommand(command);
                    break;
                case "/register":
                    registerCommand(command);
                    break;
                case "/remind":
                    remindCommand(command);
                    break;
                default:
                    System.out.println("Not recognize: " + messageFormator.getCommandFromString(command));
                    break;
            }
        else
            switch (messageFormator.getCommandFromString(command)) {
                case "/logout":
                    logoutCommand();
                    break;
                case "/search":
                    searchCommand(command);
                    break;
                default:
                    System.out.println("Not recognize: " + messageFormator.getCommandFromString(command));
                    break;
            }
    }

    private void loginCommand(String command) {
        String login = messageFormator.getLoginFromMessage(command);
        String password = messageFormator.getPasswordFromMessage(command);

        if (!ldv.validateLoginAndPassword(login, password)) {
            clientHandler.sendTxtMessage("Incorrect login data");
            return;
        }

        if (serverData.getUsers().containsKey(login)) {
            System.out.println("User login");
            if (password.equals(serverData.getUsers().get(messageFormator.getLoginFromMessage(command)))) {
                clientHandler.sendTxtMessage("Logged correctly!");
                clientDataOnServer.setLogged(true);
            } else clientHandler.sendTxtMessage("Incorrect password!");
        } else clientHandler.sendTxtMessage("User " + login + " doesn't exist.");
    }

    private void remindCommand(String command) {
        String login = messageFormator.getLoginFromMessage(command);

        if (!ldv.loginValidate(login)) return;
        System.out.println("|" + login + "|");

        if (serverData.getUsers().containsKey(login)) {
            System.out.println("Remind password");
            clientHandler.sendTxtMessage("Your password: " + serverData.getUsers().get(login));
        } else clientHandler.sendTxtMessage("User doesn't exist.");
    }

    private void logoutCommand() {
        clientDataOnServer.setLogged(false);
        System.out.println("Logged out");
    }

    private void searchCommand(String command) {
        String text = messageFormator.getSearchFromMessage(command);

        SearchByElasticsearch searchByElasticsearch = new SearchByElasticsearch("books");
        ArrayList<String> results = searchByElasticsearch.search(text);

        clientHandler.sendSDMessage(new SearchData(results));
    }

    private void registerCommand(String command) {
        String login = messageFormator.getLoginFromMessage(command);
        String password = messageFormator.getPasswordFromMessage(command);

        if (!ldv.validateLoginAndPassword(login, password)) {
            clientHandler.sendTxtMessage("Incorrect register data");
            return;
        }

        if (serverData.getUsers().containsKey(messageFormator.getLoginFromMessage(command))) {
            System.out.println("User already exist");

            clientHandler.sendTxtMessage("Username is taken");
        } else {
            System.out.println("Creating new user");

            serverData.addNewUser(login, password);

            clientHandler.sendTxtMessage("Correctly register!");
        }
    }


}
