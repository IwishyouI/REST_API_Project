package com.skyapi.weatherforecast.daily;


import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyWeatherService {


    @Autowired
    private DailyWeatherRepository dailyWeatherRepository;

    public List<DailyWeather> getListDailyWeather() {

        List<DailyWeather> listDailyWeather = (List<DailyWeather>) dailyWeatherRepository.findAll();

        if (listDailyWeather.isEmpty()) {
        }

        return listDailyWeather;
    }

    public List<DailyWeather> getByLocation(Location location) throws LocationNotFoundException {
        List<DailyWeather> getListDailyWeatherByLocation = dailyWeatherRepository.findByLocationCode(location.getCode());

        if (getListDailyWeatherByLocation == null) {
            throw new LocationNotFoundException("Location Code Not found :" + location.getCode());
        }

        return getListDailyWeatherByLocation;
    }
}
