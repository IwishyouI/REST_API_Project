package com.skyapi.weatherforecast.hourly;


import com.skyapi.weatherforecast.CommonUtility;
import com.skyapi.weatherforecast.GeolocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/hourly")
public class HourlyWeatherApiController {


    private HourlyWeatherService hourlyWeatherService;
    private GeolocationService locationService;

    public HourlyWeatherApiController(HourlyWeatherService hourlyWeatherService, GeolocationService locationService) {
        this.hourlyWeatherService = hourlyWeatherService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> listHourlyForecastByIPAddress(HttpServletRequest request) throws GeolocationException, LocationNotFoundException {


        String ipAddress = CommonUtility.getClientIP(request);


        int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));

        try {
            Location locationFromIP = locationService.getLocation(ipAddress);
            List<HourlyWeather> hourlyForecast = hourlyWeatherService.getByLocation(locationFromIP, currentHour);

            if (hourlyForecast.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(hourlyForecast);

        } catch (LocationNotFoundException e) {

            return ResponseEntity.badRequest().build();
        } catch (GeolocationException ex) {
            return ResponseEntity.notFound().build();
        }

    }
}