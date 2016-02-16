package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;


public class CarType {

    // Static HashMap which has been derived from the table in the tech test brief
    // Can be used for fast lookup
    private static final HashMap<Character, String> carTypeMap = new HashMap<>();;
    static {

        carTypeMap.put('M', "Mini");
        carTypeMap.put('E', "Economy");
        carTypeMap.put('C', "Compact");
        carTypeMap.put('I', "Intermediate");
        carTypeMap.put('S', "Standard");
        carTypeMap.put('F', "Full size");
        carTypeMap.put('P', "Premium");
        carTypeMap.put('L', "Luxury");
        carTypeMap.put('X', "Special");
    }

    private String carTypeName;

    public CarType(char character) throws KeyNotInMapException {

        this.carTypeName = getCarTypeFromMap(character);
    }

    public String getCarTypeName() {

        return carTypeName;
    }

    // Access to the static HashMap
    public static String getCarTypeFromMap(char character) throws KeyNotInMapException {

        if(carTypeMap.containsKey(character)){

            return carTypeMap.get(character);
        }
        else {

           throw new KeyNotInMapException(character);
        }
    }
}
