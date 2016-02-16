package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTypeTest {

    CarType carType;
    char mini = 'M';

    @Before
    public void setUp() throws KeyNotInMapException {

        carType = new CarType(mini);
    }

    @Test
    public void testGetCarType() {

        String carTypeString = null;

        try {
            carTypeString = CarType.getCarTypeFromMap(mini);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(carTypeString, carType.getCarTypeName());
    }

    @Test
    public void testGetCarTypeFromMap() {

        String economy = "Economy";
        char character = 'E';

        String economyFromMap = null;
        try {
            economyFromMap = CarType.getCarTypeFromMap(character);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(economy, economyFromMap);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testCreateCarTypeWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        CarType carType = new CarType(unknown);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testGetCarTypeWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        String carTypeString = CarType.getCarTypeFromMap(unknown);
    }
}
