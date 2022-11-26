package com.company;

public class LoginDataValidator {

    public boolean validateLoginAndPassword(String login, String password) {
        return loginValidate(login) && passwordValidate(password);
    }

    public boolean loginValidate(String login) {
        if (login.length() >= 3 && login.length() <= 12) {
            if (login.matches("[a-zA-Z0-9]*")) {
                return true;
            }
        }
        System.out.println("Incorrect login");
        return false;
    }

    public boolean passwordValidate(String haslo) {
        if (haslo.length() >= 4 && haslo.length() <= 16) {
            if (!haslo.matches("^\\\\s*$")) {
                return true;
            }
        }
        System.out.println("Incorrect password");
        return false;
    }
}
