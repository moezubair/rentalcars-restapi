package com.aaroncameron.rentalcars;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

public class RentalCarsConfiguration extends Configuration {

    private final String rentalCarsUrl = "http://www.rentalcars.com/js/vehicles.json";

    public String getRentalCarsUrl() {

        return rentalCarsUrl;
    }

    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

    @JsonProperty("httpClient")
    public HttpClientConfiguration getHttpClientConfiguration() {

        return httpClient;
    }

    @JsonProperty("httpClient")
    public void setHttpClientConfiguration(HttpClientConfiguration httpClient) {

        this.httpClient = httpClient;
    }
}
