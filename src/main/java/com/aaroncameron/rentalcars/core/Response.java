package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.HTTPStatusException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Response {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    private HttpClient httpClient;
    private String url;

    public Response(HttpClient httpClient, String url) {

        this.httpClient = httpClient;
        this.url = url;
    }

    public HttpResponse getResponseFromApi() throws HTTPStatusException {

        HttpGet request = new HttpGet(this.url);
        request.addHeader(CONTENT_TYPE, APPLICATION_JSON);

        HttpResponse response = null;

        try {
            response = this.httpClient.execute(request);

            return response;
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            request.releaseConnection();
        }

        return response;
    }

    public String dealWithResponseFromApi(HttpResponse response) throws HTTPStatusException {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK) {

            // Something went wrong so exit method and deal with exception elsewhere
            throw new HTTPStatusException(statusCode);
        }

        try {
            // Gets the response body as a string
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        return "";
    }
}
