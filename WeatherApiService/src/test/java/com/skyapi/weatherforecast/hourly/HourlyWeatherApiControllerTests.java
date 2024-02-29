package com.skyapi.weatherforecast.hourly;


import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HourlyWeatherApiController.class)
public class HourlyWeatherApiControllerTests {


    private static final String END_POINT_PATH = "/v1/hourly";
    private static final String X_CURRENT_HOUR = "X-Current_Hour";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HourlyWeatherService hourlyWeatherService;

    @MockBean
    private GeolocationService locationService;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testHourlyWeatherShouldReturn400BadRequest() throws Exception {

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }



    @Test
    public void testHourlyWeatherShouldReturn404NotFound() throws Exception {


        when(locationService.getLocation(Mockito.anyString())).thenThrow(GeolocationException.class);

        mockMvc.perform(get(END_POINT_PATH).header("X-Current-hour", "9"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testHourlyWeatherShouldReturn204NoContent() throws Exception {
        int currentHour = 9;
        Location location = new Location().code("DELHI_IN");


        when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(new ArrayList<>());


        mockMvc.perform(get(END_POINT_PATH).header(X_CURRENT_HOUR, currentHour))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    public void testHourlyWeatherShouldReturn200OK() throws Exception {
        int currentHour = 10;
        Location location = new Location();

        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);

        HourlyWeather forecast1 = new HourlyWeather().location(location)
                .hourOfDay(10)
                .temperature(13)
                .precipitation(70)
                .status("Cloudy");

        HourlyWeather forecast2 = new HourlyWeather().location(location)
                .hourOfDay(12)
                .temperature(16)
                .precipitation(59)
                .status("Sunny");
        var hourlyForecast = List.of(forecast1, forecast2);

        when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(hourlyForecast);


        mockMvc.perform(get(END_POINT_PATH).header(X_CURRENT_HOUR, currentHour))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is(location.toString())))
                .andDo(print());

    }

}
