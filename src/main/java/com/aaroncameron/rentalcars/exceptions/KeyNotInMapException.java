package com.aaroncameron.rentalcars.exceptions;

public class KeyNotInMapException extends Exception {

    private char c;
    private String key;

    public KeyNotInMapException(char c) {
        this.c = c;
    }

    public KeyNotInMapException(String key) {

        this.key = key;
    }

    public char getCharacterEntered() {

        return c;
    }

    public String getKey() {
        
        return key;
    }
}
