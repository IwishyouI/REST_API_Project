package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import com.skyapi.weatherforecast.location.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RealTimeWeatherService {


    private RealTimeWeatherRepository RealtimeWeatherRepo;
    private LocationRepository locationRepo;

    public RealTimeWeatherService(RealTimeWeatherRepository RealtimeWeatherRepo , LocationRepository locationRepo) {
        this.RealtimeWeatherRepo = RealtimeWeatherRepo;
        this.locationRepo = locationRepo;
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

    public RealTimeWeather getByLocationCode(String locationCode) throws LocationNotFoundException {

        RealTimeWeather realTimeWeather = RealtimeWeatherRepo.findByLocationCode(locationCode);

        if (realTimeWeather == null) {
            throw new LocationNotFoundException("No location found with the given code");
        }
        return realTimeWeather;
    }

    public RealTimeWeather update(String locationCode, RealTimeWeather realTimeWeather) throws LocationNotFoundException {
        Location location = locationRepo.findByCode(locationCode);

        if (location == null) {
            throw new LocationNotFoundException("No location found with the given code");
        }

        realTimeWeather.setLocation(location);
        realTimeWeather.setLastUpdated(new Date());

        return RealtimeWeatherRepo.save(realTimeWeather);
    }

}
