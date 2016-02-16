package com.aaroncameron.rentalcars;

import com.aaroncameron.rentalcars.resource.HighRatingPerCarTypeResource;
import com.aaroncameron.rentalcars.resource.PriceResource;
import com.aaroncameron.rentalcars.resource.SpecificationResource;
import com.aaroncameron.rentalcars.resource.SumOfScoresResource;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;

public class RentalCarsApplication extends Application<RentalCarsConfiguration> {

        public static void main(String[] args) throws Exception {
        new RentalCarsApplication().run(args);
    }

    @Override
    public String getName() {

    return "rentalcars-app";
    }

    @Override
    public void initialize(Bootstrap<RentalCarsConfiguration> bootstrap) {
    }

    @Override
    public void run(RentalCarsConfiguration rentalCarsConfiguration, Environment environment) throws Exception {

    final HttpClient httpClient = new HttpClientBuilder(environment)
            .using(rentalCarsConfiguration.getHttpClientConfiguration()).build("rentalcars-http-client");

    environment.jersey().register(new PriceResource(httpClient, rentalCarsConfiguration.getRentalCarsUrl()));
    environment.jersey().register(new SpecificationResource(httpClient, rentalCarsConfiguration.getRentalCarsUrl()));
    environment.jersey().register(new HighRatingPerCarTypeResource(httpClient, rentalCarsConfiguration.getRentalCarsUrl()));
    environment.jersey().register(new SumOfScoresResource(httpClient, rentalCarsConfiguration.getRentalCarsUrl()));
    }
}
