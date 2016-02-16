package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.InvalidSIPPCodeException;
import com.aaroncameron.rentalcars.exceptions.KeyNotInMapException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class JsonToCarListParser {

    private ArrayList<Car> allCars = new ArrayList<>();

    public JsonToCarListParser(JsonNode jsonNode) {

        this.allCars = parseJsonToListOfCars(jsonNode);
    }

    private ArrayList<Car> parseJsonToListOfCars(JsonNode jsonNode) {

        ArrayList<Car> allCars = new ArrayList<>();

        if (jsonNode != null && jsonNode.isArray()) {

            for (final JsonNode objNode : jsonNode) {

                try {

                    String sipp = objNode.findValue("sipp").asText();
                    String name = objNode.findValue("name").asText();
                    double price = objNode.findValue("price").asDouble();
                    String supplier = objNode.findValue("supplier").asText();
                    double rating = objNode.findValue("rating").asDouble();

                    Car car = new Car(sipp, name, price, supplier, rating);
                    allCars.add(car);
                } catch (NullPointerException e) {
                    // key is not in JSON so car is not created
                    System.out.println("Invalid JSON Node!");
                } catch (KeyNotInMapException e) {

                    System.out.println("Invalid SIPP character '" + e.getCharacterEntered() + "'! Car not created!");
                } catch (InvalidSIPPCodeException e) {

                    System.out.println("Invalid SIPP Code length " + e.getSippCodeSize() + "! Car not created!");
                }
            }
        }
        return allCars;
    }

    public ArrayList<Car> getAllCars() {

        return allCars;
    }
}
