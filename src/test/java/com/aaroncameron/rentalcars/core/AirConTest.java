package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirConTest {

    private AirCon airCon;
    private char aC = 'R';

    @Before
    public void setUp() throws KeyNotInMapException {

        airCon = new AirCon(aC);
    }

    @Test
    public void testGetAirCon() {

        String airConString = null;

        try {

            airConString = AirCon.getAirConFromMap(aC);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(airConString, airCon.getAirConName());
    }

    @Test
    public void testGetAirConFromMap() {

        String noAC = "no AC";
        char character = 'N';

        String noACFromMap = null;
        try {

            noACFromMap = AirCon.getAirConFromMap(character);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(noAC, noACFromMap);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testCreateAirConWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        AirCon airCon = new AirCon(unknown);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testGetAirConFromMapWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        String airConString = AirCon.getAirConFromMap(unknown);
    }
}
