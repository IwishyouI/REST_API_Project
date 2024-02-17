package com.skyapi.weatherforecast.realtime;


import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherApiController {


    private GeolocationService locationService;

    private RealTimeWeatherService realTimeWeatherService;

    public RealtimeWeatherApiController(GeolocationService locationService, RealTimeWeatherService realTimeWeatherService) {
        this.locationService = locationService;
        this.realTimeWeatherService = realTimeWeatherService;
    }


//    @GetMapping
//    public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request) {
//
//        String ipAddress = CommonUtility.getClientIP(request);
//
//        try {
//            Location location = locationService.getLocation(ipAddress);
////            realTimeWeatherService
//
//        }catch (GeolocationException) {
//
//        }
//        catch (GeolocationException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
