package com.aaroncameron.rentalcars.resource;

import com.aaroncameron.rentalcars.RentalCarsApplication;
import com.aaroncameron.rentalcars.RentalCarsConfiguration;
import com.aaroncameron.rentalcars.core.Car;
import com.aaroncameron.rentalcars.core.JsonToCarListParser;
import com.aaroncameron.rentalcars.core.Response;
import com.aaroncameron.rentalcars.exceptions.HTTPStatusException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ResourceTest {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final int PORT = 8989;
    private static final String RATING = "rating";
    private static final String PRICES = "Prices";
    private static final String SPEC = "spec";
    private static final String SCORES = "scores";
    private static final String URL = "http://localhost:";

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private PriceResource priceResource;
    private SpecificationResource specificationResource;
    private HighRatingPerCarTypeResource highRatingPerCarTypeResource;
    private SumOfScoresResource sumOfScoresResource;
    private JsonToCarListParser jsonToCarListParser;

    @Rule
    public final DropwizardAppRule<RentalCarsConfiguration> RULE =
            new DropwizardAppRule<RentalCarsConfiguration>(RentalCarsApplication.class, "rentalcars.yml");

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Before
    public void setUp() {

        httpClient = new HttpClientBuilder(RULE.getEnvironment())
                .using(RULE.getConfiguration().getHttpClientConfiguration()).build("test-http-client");

        priceResource = new PriceResource(
                httpClient, RULE.getConfiguration().getRentalCarsUrl());

        specificationResource = new SpecificationResource(
                httpClient, RULE.getConfiguration().getRentalCarsUrl());

        highRatingPerCarTypeResource = new HighRatingPerCarTypeResource(
                httpClient, RULE.getConfiguration().getRentalCarsUrl());

        sumOfScoresResource = new SumOfScoresResource(
                httpClient, RULE.getConfiguration().getRentalCarsUrl());

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testPriceResourceGetHttpResponseWithOKCode() {

        getHttpResponseWithOKCode(PRICES);
    }

    @Test
    public void testSpecificationResourceGetHttpResponseWithOKCode() {

        getHttpResponseWithOKCode(SPEC);
    }

    @Test
    public void testHighRatingPerCarTypeResourceGetHttpResponseWithOKCode() {

        getHttpResponseWithOKCode(RATING);
    }

    @Test
    public void testSumOfScoresResourceGetHttpResponseWithOKCode() {

        getHttpResponseWithOKCode(SCORES);
    }

    private void getHttpResponseWithOKCode(String affix) {
        wireMockRule.stubFor(get(urlEqualTo("/" + affix))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(fixture("fixtures/car.json"))));

        Response response = new Response(httpClient, URL + PORT + "/" + affix);
        HttpResponse httpResponse = null;
        String responseBody = null;

        try {

            httpResponse = response.getResponseFromApi();
            responseBody = response.dealWithResponseFromApi(httpResponse);
        } catch (HTTPStatusException e) {
            fail("HTTPStatusException thrown!");
            e.printStackTrace();
        }

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(httpResponse.containsHeader(APPLICATION_JSON));
    }


    @Test (expected = HTTPStatusException.class)
    public void testPriceResourceGetHttpResponseWithNotOKCode() throws HTTPStatusException {

        getHttpResponseWithNotOKCode(PRICES);
    }

    @Test (expected = HTTPStatusException.class)
    public void testSpecificationResourceGetHttpResponseWithNotOKCode() throws HTTPStatusException {

        getHttpResponseWithNotOKCode(SPEC);
    }


    @Test (expected = HTTPStatusException.class)
    public void testHighRatingPerCarTypeResourceGetHttpResponseWithNotOKCode() throws HTTPStatusException {

        getHttpResponseWithNotOKCode(RATING);
    }

    @Test (expected = HTTPStatusException.class)
    public void testSumOfScoresResourceGetHttpResponseWithNotOKCode() throws HTTPStatusException {

        getHttpResponseWithNotOKCode(SCORES);
    }

    private void getHttpResponseWithNotOKCode(String affix) throws HTTPStatusException {
        wireMockRule.stubFor(get(urlEqualTo("/" + affix))
                .willReturn(aResponse()
                        .withStatus(404)));

        Response response = new Response(httpClient, URL + PORT + "/" + affix);

        HttpResponse httpResponse = response.getResponseFromApi();
        String responseBody = response.dealWithResponseFromApi(httpResponse);
    }

    @Test
    public void testListAllVehiclePriceAsString() {

        JsonToCarListParser jsonToCarListParser = getJsonToCarListParser();
        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(allCars.get(0).getVehiclePriceString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(2).getVehiclePriceString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(1).getVehiclePriceString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(3).getVehiclePriceString());
        stringBuilder.append(System.getProperty("line.separator"));

        String priceList = priceResource.listAllVehiclePriceAsString(allCars);
        assertEquals(stringBuilder.toString(), priceList);
    }

    @Test
    public void testListAllCarSpecificationAsString() {

        JsonToCarListParser jsonToCarListParser = getJsonToCarListParser();
        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(allCars.get(0).getVehicleSpecString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(1).getVehicleSpecString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(2).getVehicleSpecString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(3).getVehicleSpecString());
        stringBuilder.append(System.getProperty("line.separator"));

        String specList = specificationResource.listAllCarSpecificationAsString(allCars);
        assertEquals(stringBuilder.toString(), specList);

    }

    @Test
    public void testListAllSumOfScoresAsString() {

        JsonToCarListParser jsonToCarListParser = getJsonToCarListParser();
        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(allCars.get(0).getCombinedScoresString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(1).getCombinedScoresString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(3).getCombinedScoresString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(2).getCombinedScoresString());
        stringBuilder.append(System.getProperty("line.separator"));

        String specList = sumOfScoresResource.listAllSumOfScoresAsString(allCars);
        assertEquals(stringBuilder.toString(), specList);
    }

    private JsonToCarListParser getJsonToCarListParser() {

        String jsonArray = "[{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
                "157.45, \"supplier\": \"Hertz\", \"rating\": 8.9 }," +
                "{ \"sipp\": \"IVMR\", \"name\": \"Vauxhall Zafira\", \"price\": " +
                "323.63, \"supplier\": \"Sixt\", \"rating\": 8.2 }," +
                "{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
                "157.85, \"supplier\": \"Alamo\", \"rating\": 7.8 }," +
                "{ \"sipp\": \"FVMR\", \"name\": \"VW Sharan\", \"price\": " +
                "753.75, \"supplier\": \"Europcar\", \"rating\": 8.0 }]";

        JsonNode jsonNodeArray = null;

        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return new JsonToCarListParser(jsonNodeArray);
    }

    @Test
    public void testlistAllRatedSupplierAsString() {

        String jsonArray = "[{ \"sipp\": \"IDMR\", \"name\": \"Ford Focus\", \"price\": " +
                "157.45, \"supplier\": \"Hertz\", \"rating\": 8.9 }," +
                "{ \"sipp\": \"IVMR\", \"name\": \"Vauxhall Zafira\", \"price\": " +
                "323.63, \"supplier\": \"Sixt\", \"rating\": 8.2 }," +
                "{ \"sipp\": \"CDMR\", \"name\": \"Ford Focus\", \"price\": " +
                "157.85, \"supplier\": \"Alamo\", \"rating\": 7.8 }," +
                "{ \"sipp\": \"CVMR\", \"name\": \"VW Sharan\", \"price\": " +
                "753.75, \"supplier\": \"Sixt\", \"rating\": 8.2 }]";

        JsonNode jsonNodeArray = null;
        try {

            jsonNodeArray = objectMapper.readTree(jsonArray);
        } catch (IOException e) {

            e.printStackTrace();
        }

        JsonToCarListParser jsonToCarListParser = new JsonToCarListParser(jsonNodeArray);
        ArrayList<Car> allCars = jsonToCarListParser.getAllCars();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(allCars.get(0).getRatedSupplierString());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(allCars.get(3).getRatedSupplierString());
        stringBuilder.append(System.getProperty("line.separator"));

        String ratedSupplierList = highRatingPerCarTypeResource.listAllRatedSupplierAsString(allCars);

        assertEquals(stringBuilder.toString(), ratedSupplierList);
    }
}
