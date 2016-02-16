package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.InvalidSIPPCodeException;
import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SIPPCodeTest {

    private SIPPCode sippCode;
    private String sipp;
    private char compact;
    private char estate;
    private char manual;
    private char fuelAirCon;

    @Before
    public void setUp() throws KeyNotInMapException {

        compact = 'C';
        estate = 'W';
        manual = 'M';
        fuelAirCon = 'R';

        StringBuilder stringBuilder = new StringBuilder();

        sipp = new StringBuilder().append(compact)
                .append(estate)
                .append(manual)
                .append(fuelAirCon).toString();

        try {

            sippCode = new SIPPCode(sipp);
        } catch (InvalidSIPPCodeException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testGetSippCodeArrayLength() {

        char[] sippArrayFromGetter = sippCode.getSippCodeArray();

        assertTrue(sippArrayFromGetter.length == 4);

    }

    @Test
    public void testGetSippCodeArray() {

        char[] sippArray = {compact, estate, manual, fuelAirCon};

        char[] sippArrayFromGetter = sippCode.getSippCodeArray();

        assertTrue(sippArrayFromGetter[0] == sippArray[0]);
        assertTrue(sippArrayFromGetter[1] == sippArray[1]);
        assertTrue(sippArrayFromGetter[2] == sippArray[2]);
        assertTrue(sippArrayFromGetter[3] == sippArray[3]);
    }

    @Test
    public void testGetCarType() {

        String carTypeName = "";

        try {

            carTypeName = CarType.getCarTypeFromMap(compact);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(carTypeName, sippCode.getCarType().getCarTypeName());
    }

    @Test
    public void testGetDoorsCarType() {

        String doorsCarTypeName = "";

        try {

            doorsCarTypeName = DoorsCarType.getDoorsCarTypeFromMap(estate);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(doorsCarTypeName, sippCode.getDoorsCarType().getDoorsCarTypeName());
    }

    @Test
    public void testGetTransmission() {

        String transmissionName = "";

        try {

            transmissionName = Transmission.getTransmissionFromMap(manual);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(transmissionName, sippCode.getTransmission().getTransmissionName());
    }

    @Test
    public void testGetFuel() {

        String fuelName = "";

        try {

            fuelName = Fuel.getFuelFromMap(fuelAirCon);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(fuelName, sippCode.getFuel().getFuelName());
    }

    @Test
    public void testGetAirCon() {

        String airConName = "";

        try {

            airConName = AirCon.getAirConFromMap(fuelAirCon);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(airConName, sippCode.getAirCon().getAirConName());
    }

    //
    @Test(expected = InvalidSIPPCodeException.class)
    public void testCreateSIPPCodeWithInvalidCodeOne() throws InvalidSIPPCodeException, KeyNotInMapException {

        SIPPCode sippCode = new SIPPCode("ASDSDS");
    }

    @Test(expected = InvalidSIPPCodeException.class)
    public void testCreateSIPPCodeWithInvalidCodeTwo() throws InvalidSIPPCodeException, KeyNotInMapException {

        SIPPCode sippCode = new SIPPCode("A");
    }
}
