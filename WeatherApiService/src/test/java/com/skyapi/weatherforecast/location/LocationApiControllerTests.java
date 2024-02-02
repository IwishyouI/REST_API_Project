package com.skyapi.weatherforecast.location;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
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
    public void testListShouldReturn204NoContent() throws Exception {

        Mockito.when(service.list()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testListShouldReturn200OK() throws Exception {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);
        location.setTrashed(false);

        Location location2 = new Location();
        location2.setCode("WSC_USA");
        location2.setCityName("Wasington DC");
        location2.setRegionName("Wasington");
        location2.setCountryCode("US");
        location2.setCountryName("United states of America");
        location2.setEnabled(true);
        location2.setTrashed(false);


        Mockito.when(service.list()).thenReturn(List.of(location2, location));

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }


    @Test
    public void testShouldReturn405MethodNotAllowed() throws Exception {
        String requestURI = END_POINT_PATH + "/ABCDEF";

        mockMvc.perform(post(requestURI))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());


    }

    @Test
    public void testShouldReturn404NotFound() throws Exception {
        String requestURI = END_POINT_PATH + "/ABCDEF";

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());


    }


    @Test
    public void testShouldReturn200OK() throws Exception {
        String requestURI = END_POINT_PATH + "/NYC_USA";

        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United states of America");
        location.setEnabled(true);
        location.setTrashed(false);

        Mockito.when(service.get("NYC_USA")).thenReturn(location);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code",is("NYC_USA")))
                .andDo(print());


    }

}

