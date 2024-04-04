package com.skyapi.weatherforecast.daily;

import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.DailyWeatherId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DailyWeatherRepository extends CrudRepository<DailyWeather , DailyWeatherId> {


    @Query("select d from DailyWeather d where d.id.location.code = ?1")
    public List<DailyWeather> findByLocationCode(String locationCode);
}
