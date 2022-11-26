package com.company;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerData {

    private ConcurrentMap<String, String> users;
    private ConcurrentMap<String, String> newUsers = new ConcurrentHashMap<>();

    private CopyOnWriteArrayList<ClientHandler> chToRemove = new CopyOnWriteArrayList<>();

    private boolean shouldRun = true;

    public boolean isShouldRun() {
        return shouldRun;
    }

    public void setUsers(ConcurrentMap<String, String> users) {
        this.users = users;
    }

    public ConcurrentMap<String, String> getUsers() {
        return users;
    }

    public boolean isNewUser() {
        return newUsers.size() > 0;
    }

    public void addChToRemove(ClientHandler ch) {
        chToRemove.addIfAbsent(ch);
    }

    public void removeChToRemove(ClientHandler ch) {
        chToRemove.remove(ch);
    }

    public CopyOnWriteArrayList<ClientHandler> getChToRemove() {
        return chToRemove;
    }

    public ConcurrentMap<String, String> getNewUsers() {
        return newUsers;
    }

    public void addNewUser(String login, String password) {
        users.putIfAbsent(login, password);
        newUsers.putIfAbsent(login, password);
    }
}
