package com.skyapi.weatherforecast.hourly;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HourlyWeatherApiController.class)
public class HourlyWeatherApiControllerTests {


    private static final String END_POINT_PATH = "/v1/hourly";
    private static final String X_CURRENT_HOUR = "X-Current-Hour";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HourlyWeatherService hourlyWeatherService;

    @MockBean
    private GeolocationService locationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHourlyWeatherShouldReturn400BadRequest() throws Exception {

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }



    @Test
    public void testHourlyWeatherShouldReturn400NotFound() throws Exception {


        Mockito.when(locationService.getLocation(Mockito.anyString())).thenThrow(GeolocationException.class);

        mockMvc.perform(get(END_POINT_PATH).header(X_CURRENT_HOUR, "9"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testHourlyWeatherShouldReturn204NoContent() throws Exception {
        int currentHour = 9;
        Location location = new Location().code("DELHI_IN");


        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(new ArrayList<>());


        mockMvc.perform(get(END_POINT_PATH).header(X_CURRENT_HOUR, currentHour))
                .andExpect(status().isNoContent())
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

        HourlyWeather forecast1 = new HourlyWeather().location(location)
                .hourOfDay(10)
                .temperature(13)
                .precipitation(70)
                .status("Cloudy");

        HourlyWeather forecast2 = new HourlyWeather().location(location)
                .hourOfDay(11)
                .temperature(16)
                .precipitation(59)
                .status("Sunny");

//        var hourlyForecast = List.of(forecast1, forecast2);
        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        Mockito.when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(List.of(forecast1, forecast2));

        String expectedLocation = location.toString();
        mockMvc.perform(get(END_POINT_PATH).header(X_CURRENT_HOUR, currentHour))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is(expectedLocation)))
                .andExpect(jsonPath("$.hourly_forecast[0].hour_of_day",is(10)))
                .andDo(print());

    }

    @Test
    public void testGetByCodeShouldReturn400BadRequest() throws Exception {

        int currentHour = 10;
        String code = "SEOUL";

        String requestURI = END_POINT_PATH + "/" + code;

        Mockito.when(hourlyWeatherService.getByLocationCode(code, currentHour)).thenThrow(LocationNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isBadRequest())
                .andDo(print());


    }


    @Test
    public void testGetByCodeShouldReturn204NoContent() throws Exception {
        int currentHour = 10;
        Location location = new Location();

        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");

        HourlyWeather forecast1 = new HourlyWeather().location(location)
                .hourOfDay(10)
                .temperature(13)
                .precipitation(70)
                .status("Cloudy");

        HourlyWeather forecast2 = new HourlyWeather().location(location)
                .hourOfDay(11)
                .temperature(16)
                .precipitation(59)
                .status("Sunny");


        String code = location.getCode();
        String requestURI = END_POINT_PATH + "/" + code;

        Mockito.when(hourlyWeatherService.getByLocationCode(code, 5)).thenReturn(List.of(forecast1, forecast2));

        mockMvc.perform(get(requestURI).header(X_CURRENT_HOUR,5))
                .andExpect(status().isBadRequest())
                .andDo(print());


    }

    @Test
    public void testShouldReturn404NotFound() throws Exception {

        int currentHour = 10;
        Location location = new Location();

        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");



        String code = location.getCode();
        String requestURI = END_POINT_PATH + "/" + code;

        Mockito.when(hourlyWeatherService.getByLocationCode("SE", 2)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(requestURI).header(X_CURRENT_HOUR,5))
                .andExpect(status().isNotFound())
                .andDo(print());



    }

    @Test
    public void updateShouldReturn400BadRequestBecauseNoData() throws Exception {
        String requestURI = END_POINT_PATH + "/NYC_USA";
        List<HourlyWeatherDTO> listDTO = Collections.emptyList();
        String requestBody = objectMapper.writeValueAsString(listDTO);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    public void updateShouldReturn400BadRequestBecauseInvalidData() throws Exception {
        String requestURI = END_POINT_PATH + "/NYC_USA";

        HourlyWeatherDTO dto1 = new HourlyWeatherDTO()
                .hourOfDay(10)
                .temperature(133)
                .precipitation(700)
                .status("Cloudy");

        HourlyWeatherDTO dto2 = new HourlyWeatherDTO()
                .hourOfDay(-1)
                .temperature(16)
                .precipitation(59)
                .status("Sunny");

        List<HourlyWeatherDTO> listDTO = List.of(dto1, dto2);
        String requestBody = objectMapper.writeValueAsString(listDTO);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }




}
