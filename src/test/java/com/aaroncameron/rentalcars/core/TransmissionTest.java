package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransmissionTest {

    private Transmission transmission;
    private char manual = 'M';

    @Before
    public void setUp() throws KeyNotInMapException {

        transmission = new Transmission(manual);
    }

    @Test
    public void testGetTransmission() {

        String transmissionString = null;

        try {
            transmissionString = Transmission.getTransmissionFromMap(manual);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(transmissionString, transmission.getTransmissionName());
    }

    @Test
    public void testTransmissionFromMap() {

        String automatic = "Automatic";
        char character = 'A';

        String transmissionFromMap = null;
        try {
            transmissionFromMap = Transmission.getTransmissionFromMap(character);
        } catch (KeyNotInMapException e) {
            e.printStackTrace();
        }

        assertEquals(automatic, transmissionFromMap);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testCreateTransmissonWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        Transmission transmission = new Transmission(unknown);
    }

    @Test(expected=KeyNotInMapException.class)
    public void testGetTransmissionFromMapWithBadChar() throws KeyNotInMapException {

        char unknown = '2';

        String transmissionString = Transmission.getTransmissionFromMap(unknown);
    }
}
