package com.aaroncameron.rentalcars.exceptions;

public class InvalidSIPPCodeException extends Exception {

    int sippCodeSize;

    public InvalidSIPPCodeException(int sippCodeSize) {

        this.sippCodeSize = sippCodeSize;
    }

    public int getSippCodeSize() {

        return sippCodeSize;
    }
}
