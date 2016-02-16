package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;

public class AirCon {

    private static final HashMap<Character, String> fuelAirConMap = new HashMap<>();
    static {
        fuelAirConMap.put('N',"no AC");
        fuelAirConMap.put('R',"AC");
    }

    private String airConName;

    public AirCon(char character) throws KeyNotInMapException {

        this.airConName = getAirConFromMap(character);
    }

    public String getAirConName() {

        return airConName;
    }

    public static String getAirConFromMap(char character) throws KeyNotInMapException {

        if(fuelAirConMap.containsKey(character)){

            return fuelAirConMap.get(character);
        }
        else {

            throw new KeyNotInMapException(character);
        }
    }
}
