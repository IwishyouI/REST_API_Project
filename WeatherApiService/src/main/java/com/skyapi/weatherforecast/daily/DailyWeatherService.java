package com.skyapi.weatherforecast.daily;


import com.skyapi.weatherforecast.common.DailyWeather;
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
}
