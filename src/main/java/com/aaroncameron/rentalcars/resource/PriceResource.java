package com.aaroncameron.rentalcars.resource;

import com.aaroncameron.rentalcars.comparators.PriceComparator;
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

@Path("/prices")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PriceResource {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient httpClient;
    private final String rentalCarsUrl;

    public PriceResource(HttpClient httpClient, String rentalCarsUrl) {

        this.httpClient = httpClient;
        this.rentalCarsUrl = rentalCarsUrl;
    }

    // This method calls the API using a HTTP Client and the Json Response is passed to another method for parsing
    @GET
    public String displayCarsByPrice() throws HTTPStatusException {

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

        return listAllVehiclePriceAsString(allCars);
    }

    // First part of the test where cars are listed in ascending order based on price
    public String listAllVehiclePriceAsString(ArrayList<Car> allCars) {

        allCars.sort(new PriceComparator());
        StringBuilder stringBuilder = new StringBuilder();

        for (Car car : allCars) {
            stringBuilder.append(car.getVehiclePriceString());
            stringBuilder.append(System.getProperty("line.separator"));
        }

        return stringBuilder.toString();
    }
}
