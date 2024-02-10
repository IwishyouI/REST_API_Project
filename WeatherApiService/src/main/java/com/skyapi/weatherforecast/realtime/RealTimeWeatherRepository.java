package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealTimeWeather;
import org.springframework.data.repository.CrudRepository;

public interface RealTimeWeatherRepository extends CrudRepository<RealTimeWeather, String> {




}
