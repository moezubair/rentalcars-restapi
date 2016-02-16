package com.aaroncameron.rentalcars.exceptions;

public class HTTPStatusException extends Exception {

    private int statusCode;

    public HTTPStatusException(int statusCode) {

        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
