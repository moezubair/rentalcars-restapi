package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FuelTest {

    private Fuel fuel;
    private char petrol = 'R';

    @Before
    public void setUp() throws KeyNotInMapException {

        fuel = new Fuel(petrol);
    }

    @Test
    public void testGetFuel() {

        String fuelString = null;

        try {

            fuelString = Fuel.getFuelFromMap(petrol);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(fuelString, fuel.getFuelName());
    }

    @Test
    public void testGetFuelFromMap() {

        String petrol = "Petrol";
        char character = 'N';

        String petrolFromMap = null;
        try {

            petrolFromMap = Fuel.getFuelFromMap(character);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(petrol, petrolFromMap);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testCreateFuelWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        Fuel fuel = new Fuel(unknown);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testGetFuelFromMapWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        String fuelString = Fuel.getFuelFromMap(unknown);
    }
}
