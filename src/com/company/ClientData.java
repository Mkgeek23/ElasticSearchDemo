// Class with data about client on server side, when he is connected

package com.company;

public class ClientData {

    private Boolean isLogged;

    public ClientData() {
        isLogged = false;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

}
