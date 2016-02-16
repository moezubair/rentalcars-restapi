package com.aaroncameron.rentalcars.core;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class JsonToCarListParserTest {

    private ObjectMapper objectMapper;
    private JsonNode jsonNodeArray;
    private ArrayList<Car> allCars;

    private JsonToCarListParser jsonToCarListParser;


    private String jsonArray = "[{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
            "157.45, \"supplier\": \"Hertz\", \"rating\": 8.9 }," +
            "{ \"sipp\": \"IVMR\", \"name\": \"Vauxhall Zafira\", \"price\": " +
            "323.63, \"supplier\": \"Sixt\", \"rating\": 8.2 }," +
            "{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
            "157.85, \"supplier\": \"Alamo\", \"rating\": 7.8 }," +
            "{ \"sipp\": \"FVMR\", \"name\": \"VW Sharan\", \"price\": " +
            "753.75, \"supplier\": \"Europcar\", \"rating\": 8.0 }]";


    @Before
    public void setUp() {

        objectMapper = new ObjectMapper();
        try {
            jsonNodeArray = objectMapper.readTree(jsonArray);
            jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testJsonToCarListParserConstructor() {

        allCars = jsonToCarListParser.getAllCars();

        assertTrue(allCars.size() == 4);

        for (Car car : allCars) {

            assertTrue(car != null);
        }
    }

    @Test
    public void testJsonToCarListParserConstructorBadSIPPCode() {

        String jsonArray = "[{ \"sipp\": \"ADMR\", \"name\": \"Ford Focus\", \"price\": " +
                "157.85, \"supplier\": \"Hertz\", \"rating\": 8.9 }]";

        JsonNode jsonNodeArray = null;
        JsonToCarListParser jsonToCarListParser = null;

        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
            jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);

        } catch (IOException e) {

            e.printStackTrace();
        }

        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        assertTrue(allCars.size() == 0);
    }

    @Test
    public void testsonToCarListParserConstructorWithBadSIPPLength() {

        String jsonArray = "[{ \"sipp\": \"A\", \"name\": \"Ford Focus\", \"price\": " +
                "157.85, \"supplier\": \"Hertz\", \"rating\": 8.9 }]";

        JsonNode jsonNodeArray = null;
        JsonToCarListParser jsonToCarListParser = null;

        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
            jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);

        } catch (IOException e) {

            e.printStackTrace();
        }

        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        assertTrue(allCars.size() == 0);
    }

    @Test(expected = JsonMappingException.class)
    public void testsonToCarListParserConstructorWithEmptyString() throws IOException {

        String jsonArray = "";

        JsonNode jsonNodeArrayLocal;
        JsonToCarListParser jsonToCarListParser = null;

        jsonNodeArrayLocal = objectMapper.readTree(jsonArray);
    }

    @Test
    public void testsonToCarListParserConstructorWithEmptyArray() {

        String jsonArray = "[{},{},{}]";

        JsonNode jsonNodeArray = null;
        JsonToCarListParser jsonToCarListParser = null;

        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
            jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);

        } catch (IOException e) {

            e.printStackTrace();
        }

        assert jsonToCarListParser != null;
        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        assertTrue(allCars.size() == 0);
    }
}
