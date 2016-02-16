package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;

public class Fuel {

    // Static hashmap which has been derived from the table in the tech test brief
    // Can be used for fast lookup
    private static final HashMap<Character, String> fuelMap = new HashMap<>();
    static {
        fuelMap.put('N',"Petrol");
        fuelMap.put('R',"Petrol");
    }

    private String fuelName;

    public Fuel(char character) throws KeyNotInMapException {

        this.fuelName = getFuelFromMap(character);
    }

    public String getFuelName() {

        return fuelName;
    }

    // Access to the static HashMap
    public static String getFuelFromMap(char character) throws KeyNotInMapException {

        if(fuelMap.containsKey(character)){

            return fuelMap.get(character);
        }
        else {

            throw new KeyNotInMapException(character);
        }
    }
}
