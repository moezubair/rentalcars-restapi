package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.comparators.PriceComparator;
import com.aaroncameron.rentalcars.comparators.RatingComparator;
import com.aaroncameron.rentalcars.comparators.SumOfScoresComparator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ComparatorTest {

    private ObjectMapper objectMapper;
    private ArrayList<Car> allCars;

    private String jsonArray = "[{ \"sipp\": \"IDMR\", \"name\": \"Ford Focus\", \"price\": " +
            "157.45, \"supplier\": \"Hertz\", \"rating\": 8.9 }," +
            "{ \"sipp\": \"IVMR\", \"name\": \"Vauxhall Zafira\", \"price\": " +
            "323.63, \"supplier\": \"Sixt\", \"rating\": 8.2 }," +
            "{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
            "157.85, \"supplier\": \"Alamo\", \"rating\": 7.8 }," +
            "{ \"sipp\": \"CVMR\", \"name\": \"VW Sharan\", \"price\": " +
            "753.75, \"supplier\": \"Sixt\", \"rating\": 8.2 }]";

    @Before
    public void setUp() {



        objectMapper = new ObjectMapper();
        JsonNode jsonNodeArray = null;

        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
        } catch (IOException e) {

            e.printStackTrace();
        }

        JsonToCarListParser jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);
        allCars = jsonToCarListParser.getAllCars();

    }

    @Test
    public void testPriceComparator() {

        ArrayList<Car> newAllCars = (ArrayList<Car>) allCars.clone();

        newAllCars.sort(new PriceComparator());

        assertEquals(newAllCars.get(0), allCars.get(0));
        assertEquals(newAllCars.get(1), allCars.get(2));
        assertEquals(newAllCars.get(2), allCars.get(1));
        assertEquals(newAllCars.get(3), allCars.get(3));
    }

    @Test
    public void testRatingComparator() {

        ArrayList<Car> newAllCars = (ArrayList<Car>) allCars.clone();

        newAllCars.sort(new RatingComparator());

        assertEquals(newAllCars.get(0), allCars.get(0));
        assertEquals(newAllCars.get(1), allCars.get(1));
        assertEquals(newAllCars.get(2), allCars.get(3));
        assertEquals(newAllCars.get(3), allCars.get(2));
    }

    @Test
    public void testSumOfScoresComparator() {

        ArrayList<Car> newAllCars = (ArrayList<Car>) allCars.clone();

        newAllCars.sort(new SumOfScoresComparator());

        assertEquals(newAllCars.get(0), allCars.get(0));
        assertEquals(newAllCars.get(1), allCars.get(1));
        assertEquals(newAllCars.get(2), allCars.get(3));
        assertEquals(newAllCars.get(3), allCars.get(2));
    }
}
