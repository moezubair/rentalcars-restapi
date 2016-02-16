package com.aaroncameron.rentalcars.resource;

import com.aaroncameron.rentalcars.comparators.RatingComparator;
import com.aaroncameron.rentalcars.core.Car;
import com.aaroncameron.rentalcars.core.JsonToCarListParser;
import com.aaroncameron.rentalcars.core.Response;
import com.aaroncameron.rentalcars.exceptions.HTTPStatusException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/rating")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class HighRatingPerCarTypeResource {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient httpClient;
    private final String rentalCarsUrl;

    public HighRatingPerCarTypeResource(HttpClient httpClient, String rentalCarsUrl) {

        this.httpClient = httpClient;
        this.rentalCarsUrl = rentalCarsUrl;
    }

    // This method calls the API using a HTTP Client and the Json Response is passed to another method for parsing
    @GET
    public String displayCarsByHighRatingPerCarType() throws HTTPStatusException {

        ArrayList<Car> allCars = new ArrayList<>();

        try {

            Response response = new Response(this.httpClient, this.rentalCarsUrl);
            HttpResponse httpResponse = response.getResponseFromApi();
            String jsonResponse = response.dealWithResponseFromApi(httpResponse);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(jsonResponse).get("Search").get("VehicleList");
            // Creates a list of cars which are parsed from the JSON response
            JsonToCarListParser jsonToCarListParser = new JsonToCarListParser(jsonNode);
            allCars = jsonToCarListParser.getAllCars();

        } catch (HTTPStatusException e) {
            // Something has failed as the status code was not 200
            throw new WebApplicationException(e.getStatusCode());
        } catch (JsonParseException e) {
            // JSON Object returned was not correct and cannot be parsed
            // Throw an internal server error
            throw new WebApplicationException(500);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return listAllRatedSupplierAsString(allCars);
    }

    // Third part of the test where a list is created for the highest rated supplier per car type
    // The list is also sorted into descending order
    public static String listAllRatedSupplierAsString(ArrayList<Car> allCars) {

        ArrayList<Car> carHighRatingPerTypeList = getHighestSupplierRatingPerCarTypeList(allCars);

        carHighRatingPerTypeList.sort(new RatingComparator());

        StringBuilder stringBuilder = new StringBuilder();

        for (Car car : carHighRatingPerTypeList) {

            stringBuilder.append(car.getRatedSupplierString());
            stringBuilder.append(System.getProperty("line.separator"));
        }

        return stringBuilder.toString();
    }

    // Method takes a Car Array List and returns a list of highest rated suppliers per car type
    private static ArrayList<Car> getHighestSupplierRatingPerCarTypeList(ArrayList<Car> allCars) {

        // Main part of the algorithm is performed in the getHashMapOfHighRatedCarType() method
        HashMap<Character, Car> carTypeMap = getHashMapOfHighRatedCarType(allCars);

        ArrayList<Car> newCarList = convertHashMapToArrayList(carTypeMap);

        return newCarList;
    }

    //Method will create a HashMap where the car type is a key and the value is the highest rated supplier
    private static HashMap<Character, Car> getHashMapOfHighRatedCarType(ArrayList<Car> allCars) {

        HashMap<Character, Car> carTypeMap = new HashMap<>();

        for (Car car : allCars) {

            // sipp character array element at 0 contains the code for the car type
            char carTypeKey = car.getSippCodeCharArray()[0];

            // If no key exists then add the car to the map
            if(!carTypeMap.containsKey(carTypeKey)) {

                carTypeMap.put(carTypeKey, car);
            }
            else {
                // If key does exist then need to check for the higher rating and replace car if necessary
                if(car.getRating() > carTypeMap.get(carTypeKey).getRating()) {

                    carTypeMap.replace(carTypeKey, car);
                }
            }
        }
        return carTypeMap;
    }

    // Converts a HashMap to an ArrayList by traversing over the set of keys
    private static ArrayList<Car> convertHashMapToArrayList(HashMap<Character, Car> carTypeMap) {

        ArrayList<Car> newCarList = new ArrayList<>();

        for (Character character: carTypeMap.keySet()) {

            newCarList.add(carTypeMap.get(character));
        }

        return newCarList;
    }
}
