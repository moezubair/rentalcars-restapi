package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.InvalidSIPPCodeException;
import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTest {

    private Car car;
    private char carTypeChar;
    private char doorsCarTypeChar;
    private char transmissionChar;
    private char fuelAirConChar;
    private String sipp;
    private String name;
    private double price;
    private String supplier;
    private double rating;

    @Before
    public void setUp() throws InvalidSIPPCodeException {

        carTypeChar = 'C';
        doorsCarTypeChar = 'D';
        transmissionChar = 'M';
        fuelAirConChar = 'R';

        StringBuilder stringBuilder = new StringBuilder();

        sipp = new StringBuilder().append(carTypeChar)
                                  .append(doorsCarTypeChar)
                                  .append(transmissionChar)
                                  .append(fuelAirConChar).toString();
        name = "Ford Focus";
        price = 157.85;
        supplier = "Hertz";
        rating = 8.9;

        try {

            car = new Car(sipp, name, price, supplier, rating);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testGetSIPPCode() {

        SIPPCode sippCode = null;
        try {

            sippCode = new SIPPCode(sipp);
        } catch (InvalidSIPPCodeException | KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(sippCode.getCarType().getCarTypeName(),
                (car.getSIPPCode().getCarType().getCarTypeName()));
        assertEquals(sippCode.getDoorsCarType().getDoorsCarTypeName(),
                (car.getSIPPCode().getDoorsCarType().getDoorsCarTypeName()));
        assertEquals(sippCode.getTransmission().getTransmissionName(),
                (car.getSIPPCode().getTransmission().getTransmissionName()));
        assertEquals(sippCode.getAirCon().getAirConName(),
                (car.getSIPPCode().getAirCon().getAirConName()));
        assertEquals(sippCode.getFuel().getFuelName(),
                (car.getSIPPCode().getFuel().getFuelName()));
    }

    @Test
    public void testGetPrice() {

        double carPrice = car.getPrice();

        assertTrue(price == carPrice);
    }

    @Test
    public void testGetRating() {

        double carRating = car.getRating();

        assertTrue(rating == carRating);
    }

    @Test
    public void testGetSumOfScores() {

        int manual = 0;
        int airCon = 0;

        try {

            // Manual = 1
            manual = Car.getVehicleScoreFromMap("Manual");
            // Petrol/AC = 2
            airCon = Car.getVehicleScoreFromMap("AC");
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertTrue(rating == 8.9);
        double carRating = rating;

        //1 + 2 + 8.9 = 11.9
        double carScore = manual + airCon + carRating;
        assertTrue(carScore == car.getSumOfScores());
    }

    @Test
    public void testGetCarType() {

        String carType = null;
        try {

            carType = CarType.getCarTypeFromMap(carTypeChar);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(car.getCarType().getCarTypeName(), (carType));
    }

    @Test
    public void testGetSIPPCodeArray() {

        char[] sippCharArray = {carTypeChar, doorsCarTypeChar, transmissionChar, fuelAirConChar};

        assertEquals(sippCharArray[0], car.getSippCodeCharArray()[0]);
        assertEquals(sippCharArray[1], car.getSippCodeCharArray()[1]);
        assertEquals(sippCharArray[2], car.getSippCodeCharArray()[2]);
        assertEquals(sippCharArray[3], car.getSippCodeCharArray()[3]);
    }

    @Test
    public void testGetDoorsGetCarType() {

        String doorsCarType = null;
        try {

            doorsCarType = DoorsCarType.getDoorsCarTypeFromMap(doorsCarTypeChar);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(car.getDoorsCarType().getDoorsCarTypeName(), (doorsCarType));
    }

    @Test
    public void testGetTransmission() {

        String transmission = null;
        try {

            transmission = Transmission.getTransmissionFromMap(transmissionChar);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(car.getTransmission().getTransmissionName(), (transmission));
    }

    @Test
    public void testGetFuel() {

        String fuel = null;
        try {

            fuel = Fuel.getFuelFromMap(fuelAirConChar);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(car.getFuel().getFuelName(), (fuel));
    }

    @Test
    public void testGetAirCon() {

        String airCon = null;
        try {

            airCon = AirCon.getAirConFromMap(fuelAirConChar);
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(car.getAirCon().getAirConName(), (airCon));
    }

    @Test
    public void testGetVehicleScoreFromMap() {

        int manual = 0;
        int automatic = 0;
        int aircon = 0;
        try {

            manual = Car.getVehicleScoreFromMap("Manual");
            automatic = Car.getVehicleScoreFromMap("Automatic");
            aircon = Car.getVehicleScoreFromMap("AC");
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(1, manual);
        assertEquals(5, automatic);
        assertEquals(2, aircon);
    }

    @Test(expected = KeyNotInMapException.class)
    public void testGetVehicleScoreFromMapWithBadString() throws KeyNotInMapException {

        int vehicleScore = Car.getVehicleScoreFromMap("A");
    }

    @Test
    public void testGetVehiclePriceString() {

        String priceString = "{" + name + "} – " +
                             "{£" + price + "}";

        assertEquals(priceString, car.getVehiclePriceString());
    }

    @Test
    public void testGetVehicleSpecString() {

        String specString = null;
        try {

            specString = "{" + this.name + "} – " +
                         "{" + this.price + "} – " +
                         "{" + CarType.getCarTypeFromMap(carTypeChar) + "} – " +
                         "{" + DoorsCarType.getDoorsCarTypeFromMap(doorsCarTypeChar)+ "} – " +
                         "{" + Transmission.getTransmissionFromMap(transmissionChar) + "} – " +
                         "{" + Fuel.getFuelFromMap(fuelAirConChar) + "} - "  +
                         "{" + AirCon.getAirConFromMap(fuelAirConChar) + "}";
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(specString, car.getVehicleSpecString());
    }

    @Test
    public void testGetRatedSupplierString() {

        String ratedSupplierString = null;
        try {

            ratedSupplierString = "{" + name + "} – " +
                                  "{" + CarType.getCarTypeFromMap(carTypeChar) + "} – " +
                                  "{" + supplier + "} – " +
                                  "{" + rating + "}";
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }

        assertEquals(ratedSupplierString, car.getRatedSupplierString());
    }

    @Test
    public void testGetCombinedScoreString() {

        int vehicleScore = 0;
        try {

            vehicleScore = (int) (Car.getVehicleScoreFromMap("Manual") + Car.getVehicleScoreFromMap("AC"));
        } catch (KeyNotInMapException e) {

            e.printStackTrace();
        }
        double sumOfScores = vehicleScore + rating;

        String combinedScoreString = "{" + name + "} – " +
                                     "{" + vehicleScore + "} – " +
                                     "{" + rating + "} – " +
                                     "{" + sumOfScores + "}";

        assertEquals(combinedScoreString, car.getCombinedScoresString());
    }

    @Test(expected=InvalidSIPPCodeException.class)
    public void testCreateCarWithInvalidSIPPCodeLengthOne() throws InvalidSIPPCodeException, KeyNotInMapException {

        String sipp = "CDMRA";
        String name = "Ford Focus";
        double price = 157.85;
        String supplier = "Hertz";
        double rating = 8.9;

        car = new Car(sipp, name, price, supplier, rating);
    }

    @Test(expected=InvalidSIPPCodeException.class)
    public void testCreateCarWithInvalidSIPPCodeLengthTwo() throws InvalidSIPPCodeException, KeyNotInMapException {

        String sipp = "CDA";
        String name = "Ford Focus";
        double price = 157.85;
        String supplier = "Hertz";
        double rating = 8.9;

        car = new Car(sipp, name, price, supplier, rating);
    }
}
