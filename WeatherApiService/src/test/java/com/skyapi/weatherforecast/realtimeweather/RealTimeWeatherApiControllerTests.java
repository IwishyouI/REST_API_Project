package com.skyapi.weatherforecast.realtimeweather;


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


}
