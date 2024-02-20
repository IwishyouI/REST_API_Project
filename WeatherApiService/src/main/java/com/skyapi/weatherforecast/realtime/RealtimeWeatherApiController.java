package com.skyapi.weatherforecast.realtime;


import com.skyapi.weatherforecast.CommonUtility;
import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherApiController.class);
    private GeolocationService locationService;

    private RealTimeWeatherService realTimeWeatherService;

    private ModelMapper modelMapper;


    public RealtimeWeatherApiController(GeolocationService locationService, RealTimeWeatherService realTimeWeatherService, ModelMapper modelMapper) {
        this.locationService = locationService;
        this.realTimeWeatherService = realTimeWeatherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request) {

        String ipAddress = CommonUtility.getClientIP(request);

        try {
            Location locationFromIP = locationService.getLocation(ipAddress);
            RealTimeWeather realTimeWeather = realTimeWeatherService.getByLocation(locationFromIP);
            RealTimeWeatherDTO dto = modelMapper.map(realTimeWeather, RealTimeWeatherDTO.class);
            return ResponseEntity.ok(dto);
        } catch (GeolocationException e) {
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e) {
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }



    }
}
