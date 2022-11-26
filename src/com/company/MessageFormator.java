package com.company;

public class MessageFormator {

    public String getLoginFromMessage(String string) {
        int index1, index2;
        if ((index1 = string.indexOf(" ")) != -1) {
            if ((index2 = string.indexOf(" ", index1 + 1)) != -1) {
                return string.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    public String getSearchFromMessage(String string) {
        int index1;
        if ((index1 = string.indexOf(" ")) != -1) {
            return string.substring(index1 + 1);
        }
        return null;
    }

    public String getPasswordFromMessage(String string) {
        int index;
        if ((index = string.indexOf(" ")) != -1) {
            if ((index = string.indexOf(" ", index + 1)) != -1) {
                return string.substring(index + 1);
            }
        }
        return null;
    }

    public String getCommandFromString(String string) {
        int index;
        if ((index = string.indexOf(" ")) != -1) {
            return string.substring(0, index);
        }
        return null;
    }

}
