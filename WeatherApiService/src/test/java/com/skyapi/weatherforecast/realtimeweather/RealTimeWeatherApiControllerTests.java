package com.skyapi.weatherforecast.realtimeweather;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import com.skyapi.weatherforecast.realtime.RealTimeWeatherService;
import com.skyapi.weatherforecast.realtime.RealtimeWeatherApiController;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RealtimeWeatherApiController.class)
public class RealTimeWeatherApiControllerTests {

    private static final String END_POINT_PATH = "/v1/realtime";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GeolocationService locationService;
    @MockBean
    private RealTimeWeatherService realTimeWeatherService;


    @Test
    public void testRealTimeWeatherShouldReturnBadRequest() throws Exception {


        Mockito.when(locationService.getLocation(Mockito.anyString())).thenThrow(GeolocationException.class);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    public void testRealTimeWeatherShouldReturnNotFound() throws Exception {

        Location location = new Location();

        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        Mockito.when(realTimeWeatherService.getByLocation(location)).thenThrow(LocationNotFoundException.class);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNotFound())
                .andDo(print());

    }




    @Test
    public void testRealTimeWeatherShouldReturn200OK() throws Exception {

        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);
        location.setTrashed(false);

        RealTimeWeather realTimeWeather = new RealTimeWeather();

        realTimeWeather.setTemperature(-2);
        realTimeWeather.setHumidity(32);
        realTimeWeather.setPrecipitation(42);
        realTimeWeather.setStatus("Snowy");
        realTimeWeather.setWindSpeed(12);
        realTimeWeather.setLastUpdated(new Date());
        realTimeWeather.setLocation(location);


        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        Mockito.when(realTimeWeatherService.getByLocation(location)).thenReturn(realTimeWeather);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.temperature", is(-2)))
                .andDo(print());

    }

    @Test
    public void testGetByLocationCodeShouldReturn404NotFound() throws Exception {
        String code = "abc";

        Mockito.when(realTimeWeatherService.getByLocationCode(code)).thenThrow(LocationNotFoundException.class);

        mockMvc.perform(get(END_POINT_PATH + "/" + code))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetByLocationCodeShouldReturn200OK() throws Exception {
        Location location = new Location();
        location.setCode("DANA_VN");
        location.setCityName("Da nang");
        location.setCountryCode("VN");
        location.setCountryName("Vietnam");
        location.setEnabled(true);
        location.setTrashed(false);
        RealTimeWeather realTimeWeather = new RealTimeWeather();

        realTimeWeather.setTemperature(-2);
        realTimeWeather.setHumidity(32);
        realTimeWeather.setPrecipitation(42);
        realTimeWeather.setStatus("Snowy");
        realTimeWeather.setWindSpeed(12);
        realTimeWeather.setLastUpdated(new Date());
        realTimeWeather.setLocation(location);

        String code = location.getCode();


        Mockito.when(realTimeWeatherService.getByLocationCode(code)).thenReturn(realTimeWeather);

        mockMvc.perform(get(END_POINT_PATH + "/" + code))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.location",is("Da nang , null , Vietnam")))
                .andDo(print());

    }

    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        String locationCode = "ABC_US";
        String requestURI = END_POINT_PATH + "/" + locationCode;

        RealTimeWeather realTimeWeather = new RealTimeWeather();

        realTimeWeather.setTemperature(-100);
        realTimeWeather.setHumidity(32);
        realTimeWeather.setPrecipitation(42);
        realTimeWeather.setStatus("Snowy");
        realTimeWeather.setWindSpeed(12);
        realTimeWeather.setLastUpdated(new Date());


        String bodyContent = objectMapper.writeValueAsString(realTimeWeather);

        Mockito.when(realTimeWeatherService.update(locationCode, realTimeWeather)).thenReturn(realTimeWeather);

        mockMvc.perform(put(requestURI).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    public void testUpdateShouldReturn200OK() throws Exception {

        String locationCode = "DANA_VN";
        String requestURI = END_POINT_PATH + "/" + locationCode;

        RealTimeWeather realTimeWeather = new RealTimeWeather();


        realTimeWeather.setTemperature(0);
        realTimeWeather.setHumidity(15);
        realTimeWeather.setPrecipitation(3);
        realTimeWeather.setStatus("Sunny");
        realTimeWeather.setWindSpeed(3);
        realTimeWeather.setLastUpdated(new Date());

        Location location = new Location();
        location.setCode("DANA_VN");
        location.setCityName("Da nang");
        location.setCountryCode("VN");
        location.setCountryName("Vietnam");
        location.setEnabled(true);
        location.setTrashed(false);
        location.setRealtimeWeather(realTimeWeather);
        realTimeWeather.setLocation(location);
        String bodyContent = objectMapper.writeValueAsString(realTimeWeather);

        Mockito.when(realTimeWeatherService.update(locationCode, realTimeWeather)).thenReturn(realTimeWeather);

        String expectedContent = location.getCityName() + " , " + location.getRegionName() + " , " + location.getCountryName();

        mockMvc.perform(put(requestURI).contentType("application/json").content(bodyContent))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$."))
                .andDo(print());


    }


}
