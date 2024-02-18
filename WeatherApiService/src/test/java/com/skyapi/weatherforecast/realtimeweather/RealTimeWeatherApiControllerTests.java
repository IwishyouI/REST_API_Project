package com.skyapi.weatherforecast.realtimeweather;


import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.realtime.RealTimeWeatherService;
import com.skyapi.weatherforecast.realtime.RealtimeWeatherApiController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RealtimeWeatherApiController.class)
public class RealTimeWeatherApiControllerTests {

    private static final String END_POINT_PATH = "/v1/realtime";

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



}
