package com.skyapi.weatherforecast.RestClient.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ListLocationsAsJSONString {

    public static void main(String[] args) {

        String requestURI = "http://localhost:8080/location";

        RestTemplate rt = new RestTemplate();

        try {
            String response = rt.getForObject(requestURI, String.class);
            System.out.println(response);
        } catch (HttpClientErrorException ex) {
            HttpStatus statusCode = ex.getStatusCode();
            System.out.println(statusCode);
        }
    }
}
