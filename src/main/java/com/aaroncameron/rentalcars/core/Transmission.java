package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;

public class Transmission {

    // Static HashMap which has been derived from the table in the tech test brief
    // Can be used for fast lookup
    private static final HashMap<Character, String> transmissionMap = new HashMap<>();
    static {
        transmissionMap.put('M',"Manual");
        transmissionMap.put('A',"Automatic");
    }

    private String tranmissionName;

    public Transmission(char code) throws KeyNotInMapException {

        this.tranmissionName = getTransmissionFromMap(code);
    }

    public static HashMap<Character, String> getTransmissionMap() {
        return transmissionMap;
    }

    public String getTransmissionName() {

        return tranmissionName;
    }

    // Access to the static HashMap
    public static String getTransmissionFromMap(char character) throws KeyNotInMapException {

        if(transmissionMap.containsKey(character)){

            return transmissionMap.get(character);
        }
        else {

            throw new KeyNotInMapException(character);
        }
    }
}
