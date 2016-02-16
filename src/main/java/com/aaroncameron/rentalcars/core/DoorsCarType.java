package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;

public class DoorsCarType {

    // Static HashMap which has been derived from the table in the tech test brief
    // Can be used for fast lookup
    private static final HashMap<Character, String> doorsCarTypeMap = new HashMap<>();
    static {
        doorsCarTypeMap.put('B',"2 doors");
        doorsCarTypeMap.put('C',"4 doors");
        doorsCarTypeMap.put('D',"5 doors");
        doorsCarTypeMap.put('W',"Estate");
        doorsCarTypeMap.put('T',"Convertible");
        doorsCarTypeMap.put('F',"SUV");
        doorsCarTypeMap.put('P',"Pick up");
        doorsCarTypeMap.put('V',"Passenger Van");
    }

    private String doorsCarTypeName;

    public DoorsCarType(char code) throws KeyNotInMapException {

        this.doorsCarTypeName = getDoorsCarTypeFromMap(code);
    }

    public String getDoorsCarTypeName() {

        return doorsCarTypeName;
    }

    // Access to the static HashMap
    public static String getDoorsCarTypeFromMap(char character) throws KeyNotInMapException {

        if(doorsCarTypeMap.containsKey(character)){

            return doorsCarTypeMap.get(character);
        }
        else {

            throw new KeyNotInMapException(character);
        }
    }
}
