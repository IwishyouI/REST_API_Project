package com.skyapi.weatherforecast.location;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IP2LocationTests {

    private String DBPath = "C:\\Users\\dlwhd\\OneDrive\\문서\\RestAPI_Project\\WeatherApiService\\src\\main\\resources\\ip2location\\IP2LOCATION-LITE-DB3.BIN";

    @Test
    public void testInvalidIP() throws IOException {
        IP2Location ip2Location = new IP2Location();

        ip2Location.Open(DBPath);
        String ipAddress = "asvasva";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);
        System.out.println(ipResult);

//        assertThat(ipResult.getStatus()).isEqualTo("INVALID_IP_ADDRESS");
    }

    @Test
    public void testValidIP1() throws IOException {
        IP2Location ip2Location = new IP2Location();
        String ipAddress = "123.214.4.8";
        ip2Location.Open(DBPath);
        IPResult ipResult = ip2Location.IPQuery(ipAddress);
        System.out.println(ipResult);

        assertThat(ipResult.getCity()).isEqualTo("Seoul");
        assertThat(ipResult.getStatus()).isEqualTo("OK");
    }

    @Test
    public void testValidIP3() throws IOException {
        IP2Location ip2Location = new IP2Location();
        String ipAddress = "103.48.198.141";
        ip2Location.Open(DBPath);
        IPResult ipResult = ip2Location.IPQuery(ipAddress);
        System.out.println(ipResult);

    }
}
