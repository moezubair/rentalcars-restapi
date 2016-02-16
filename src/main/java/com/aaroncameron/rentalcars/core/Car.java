package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.InvalidSIPPCodeException;
import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;

import java.util.HashMap;

public class Car {

    // Static hashmap which contains the breakdown scores for vehicles
    // Can be used for fast lookup
    private static final HashMap<String, Integer> vehicleScoreMap = new HashMap<>();
    static {
        vehicleScoreMap.put("Manual", 1);
        vehicleScoreMap.put("Automatic", 5);
        vehicleScoreMap.put("AC", 2);
        vehicleScoreMap.put("no AC", 0);
    }

    private SIPPCode sipp;
    private String name;
    private double price;
    private String supplier;
    private double rating;

    private int vehicleScore;
    private double sumOfScores;

    public Car(String sipp, String name, double price, String supplier, double rating)
            throws InvalidSIPPCodeException, KeyNotInMapException {

        this.sipp = new SIPPCode(sipp);
        this.name = name;
        this.price = price;
        this.supplier = supplier;
        this.rating = rating;

        this.vehicleScore = getVehicleScore();
        this.sumOfScores = getSumOfScore(this.vehicleScore, getRating());
    }

    public SIPPCode getSIPPCode() {

        return sipp;
    }

    public double getPrice() {

        return price;
    }

    public double getRating() {

        return rating;
    }

    public double getSumOfScores() {

        return sumOfScores;
    }

    public CarType getCarType() {

        return sipp.getCarType();
    }

    public char[] getSippCodeCharArray() {

        return sipp.getSippCodeArray();
    }

    public DoorsCarType getDoorsCarType() {

        return sipp.getDoorsCarType();
    }

    public Transmission getTransmission() {

        return sipp.getTransmission();
    }

    public AirCon getAirCon() {

        return sipp.getAirCon();
    }

    public Fuel getFuel() {

        return sipp.getFuel();
    }

    // String method for part 1 of part 1
    public String getVehiclePriceString() {

        return "{" + this.name + "} – " +
                "{£" + this.price + "}";
    }

    // String method for part 2 of part 1
    public String getVehicleSpecString() {

        return "{" + this.name + "} – " +
                "{" + this.price + "} – " +
                "{" + getCarType().getCarTypeName() + "} – " +
                "{" + getDoorsCarType().getDoorsCarTypeName()+ "} – " +
                "{" + getTransmission().getTransmissionName() + "} – " +
                "{" + getFuel().getFuelName() + "} - "  +
                "{" + getAirCon().getAirConName() + "}";

    }

    // String method for part 3 of part 1
    public String getRatedSupplierString() {

        return "{" + this.name + "} – " +
                "{" + getCarType().getCarTypeName() + "} – " +
                "{" + this.supplier + "} – " +
                "{" + this.rating + "}";
    }

    // String method for part 4 of part 1
    public String getCombinedScoresString() {

        return "{" + this.name + "} – " +
                "{" + this.vehicleScore + "} – " +
                "{" + this.rating + "} – " +
                "{" + this.sumOfScores + "}";
    }

    // Wrapper which allows access to the hashmap and throws an exception if an incorrect key is supplied as a parameter
    public static int getVehicleScoreFromMap(String key) throws KeyNotInMapException {

        if(vehicleScoreMap.containsKey(key)) {
            return vehicleScoreMap.get(key);
        }
        else {
            throw new KeyNotInMapException(key);
        }
    }

    // Calculates the vehicle score using transmission and air con
    private int getVehicleScore() throws KeyNotInMapException {

        int vehicleScore = 0;

        vehicleScore += getVehicleScoreFromMap(getTransmission().getTransmissionName());
        vehicleScore += getVehicleScoreFromMap(getAirCon().getAirConName());

        return vehicleScore;
    }

    // Calculates the combined and score, in a separate method for code readability
    private double getSumOfScore(int vehicleScore, double supplierRating) {

        return vehicleScore + supplierRating;
    }
}
