package com.aaroncameron.rentalcars.core;

import com.aaroncameron.rentalcars.exceptions.HTTPStatusException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseTest {

    private String url;
    private HttpClient mockHttpClient;
    private Response response;
    private HttpResponse mockHttpResponse;
    private HttpEntity mockHttpEntity;
    private InputStream jsonResponse;
    private StatusLine mockStatusLine;

    @Before
    public void setUp() {

        url = "www.test.com";
        mockHttpClient = mock(HttpClient.class);
        response = new Response(mockHttpClient, url);
        mockHttpResponse = mock(HttpResponse.class);
        mockHttpEntity = mock(HttpEntity.class);
        mockStatusLine = mock(StatusLine.class);

        jsonResponse = new ByteArrayInputStream(fixture("fixtures/car.json").getBytes());
    }

    @Test
    public void testOKCodeResponse() throws IOException {

        try {

            when(response.getResponseFromApi()).thenReturn(mockHttpResponse);
            // Mock the response call so that json object is returned
            when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
            when(mockHttpEntity.getContent()).thenReturn(jsonResponse);
            when(mockStatusLine.getStatusCode()).thenReturn(200);
            when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);

            String responseString = response.dealWithResponseFromApi(mockHttpResponse);

            assertEquals(fixture("fixtures/car.json"), responseString);

        } catch (HTTPStatusException e) {

            fail("HTTPStatusException thrown!");
        }
    }

    @Test(expected = HTTPStatusException.class)
    public void testNotOKCodeResponse() throws IOException, HTTPStatusException {

        when(response.getResponseFromApi()).thenReturn(mockHttpResponse);
        // Mock the response call so that json object is returned
        when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
        when(mockHttpEntity.getContent()).thenReturn(jsonResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(404);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);

        String responseString = response.dealWithResponseFromApi(mockHttpResponse);
    }
}
