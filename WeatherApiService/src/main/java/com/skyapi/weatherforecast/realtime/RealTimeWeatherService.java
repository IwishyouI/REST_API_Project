package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import com.skyapi.weatherforecast.location.LocationRepository;
import org.springframework.stereotype.Service;


@Service
public class RealTimeWeatherService {


    private RealTimeWeatherRepository RealtimeWeatherRepo;

    public RealTimeWeatherService(RealTimeWeatherRepository RealtimeWeatherRepo) {
        this.RealtimeWeatherRepo = RealtimeWeatherRepo;
    }



    public RealTimeWeather getByLocation(Location location) throws LocationNotFoundException {
        String code = location.getCode();
        String cityName = location.getCityName();

        RealTimeWeather realTimeWeather = RealtimeWeatherRepo.findByCountryCodeAndCityName(code, cityName);

        if (realTimeWeather == null) {
            throw new LocationNotFoundException("No location found with the given country code and cityName");
        }

        return realTimeWeather;
    }

}
