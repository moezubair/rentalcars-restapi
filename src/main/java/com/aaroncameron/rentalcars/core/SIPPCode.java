package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.InvalidSIPPCodeException;
import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

public class SIPPCode {

    private char[] sippCodeArray;
    private CarType carType;
    private DoorsCarType doorsCarType;
    private Transmission transmission;
    private AirCon airCon;
    private Fuel fuel;

    public SIPPCode(String sipp) throws InvalidSIPPCodeException, KeyNotInMapException {

        int sippCodeSize = sipp.length();

        if(sippCodeSize == 4) {

            sippCodeArray = sipp.toCharArray();

            carType = new CarType(sippCodeArray[0]);
            doorsCarType = new DoorsCarType(sippCodeArray[1]);
            transmission = new Transmission(sippCodeArray[2]);
            airCon = new AirCon(sippCodeArray[3]);
            fuel = new Fuel(sippCodeArray[3]);
        }
        else {

            throw new InvalidSIPPCodeException(sippCodeSize);
        }
    }

    public char[] getSippCodeArray() {

        return sippCodeArray;
    }

    public CarType getCarType() {

        return carType;
    }

    public DoorsCarType getDoorsCarType() {

        return doorsCarType;
    }

    public Transmission getTransmission() {

        return transmission;
    }

    public AirCon getAirCon() {

        return airCon;
    }

    public Fuel getFuel() {

        return fuel;
    }
}
