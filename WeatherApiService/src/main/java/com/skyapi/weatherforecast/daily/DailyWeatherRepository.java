package com.skyapi.weatherforecast.daily;

import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.DailyWeatherId;
import org.springframework.data.repository.CrudRepository;

public interface DailyWeatherRepository extends CrudRepository<DailyWeather , DailyWeatherId> {
}
