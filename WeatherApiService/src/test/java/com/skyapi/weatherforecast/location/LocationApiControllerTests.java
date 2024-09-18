package com.skyapi.weatherforecast.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTests {

    private static final String END_POINT_PATH = "/v1/locations";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    LocationService service;


    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {

        Location location = new Location();

        String bodyContent = mapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }


    @Test
    public void testAddShouldReturn201Created() throws Exception {


        Location location = new Location();

        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(service.add(location)).thenReturn(location);

        String bodyContent = mapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code", Matchers.is("NYC_USA")))
                .andExpect(jsonPath("$.city_name", Matchers.is("New York City")))
                .andExpect(header().string("Location", "/v1/locations/NYC_USA"))
                .andDo(print());

    }


    @Test
    public void testListShouldReturn204NoContent() throws Exception {

        Mockito.when(service.list()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    public void testListShouledReturn200Ok() throws Exception {

        Location location1 = new Location();

        location1.setCode("NYC_USA");
        location1.setCityName("New York City");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountryName("United States of America");
        location1.setEnabled(true);


        Location location2 = new Location();

        location2.setCode("LACA_USA");
        location2.setCityName("Los Angeles");
        location2.setRegionName("California");
        location2.setCountryCode("US");
        location2.setCountryName("United States of America");
        location2.setEnabled(true);


        Location location3 = new Location();

        location3.setCode("DELHI_IN");
        location3.setCityName("New Delhi");
        location3.setRegionName("Delhi");
        location3.setCountryCode("IN");
        location3.setCountryName("India");
        location3.setEnabled(true);
        location3.setTrashed(false);

        Mockito.when(service.list()).thenReturn(List.of(location1, location2, location3));

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code",Matchers.is("NYC_USA")))
                .andExpect(jsonPath("$[0].city_name",Matchers.is("New York City")))
                .andDo(print());


    }

}
