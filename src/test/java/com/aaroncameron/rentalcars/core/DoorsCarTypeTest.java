package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoorsCarTypeTest {

    private DoorsCarType doorsCarType;
    private char estate = 'W';

    @Before
    public void setUp() throws KeyNotInMapException {

        doorsCarType = new DoorsCarType(estate);
    }

    @Test
    public void testGetDoorsCarType() {

        String doorsCarTypeString = null;

        try {
            doorsCarTypeString = DoorsCarType.getDoorsCarTypeFromMap(estate);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(doorsCarTypeString, doorsCarType.getDoorsCarTypeName());
    }

    @Test
    public void testGetDoorCarTypeFromMap() {

        String estate = "Estate";
        char character = 'W';

        String estateFromMap = null;
        try {
            estateFromMap = DoorsCarType.getDoorsCarTypeFromMap(character);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(estate, estateFromMap);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testCreateDoorsCarTypeWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        DoorsCarType doorsCarType = new DoorsCarType(unknown);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testGetDoorsCarTypeWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        String doorsCarTypeString = DoorsCarType.getDoorsCarTypeFromMap(unknown);
    }
}
