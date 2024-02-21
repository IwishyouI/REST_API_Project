package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealTimeWeather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RealTimeWeatherRepository extends CrudRepository<RealTimeWeather, String> {


    @Query("select r from RealTimeWeather r where r.location.countryCode  = ?1 and r.location.cityName = ?2")
    public RealTimeWeather findByCountryCodeAndCityName(String countryCode, String cityName);

    @Query("select r from RealTimeWeather r where r.location.code  = ?1 and r.location.trashed = false")
    public RealTimeWeather findByLocationCode(String locationCode);



}
